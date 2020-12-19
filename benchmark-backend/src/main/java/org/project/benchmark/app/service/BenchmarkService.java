package org.project.benchmark.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.benchmark.algorithm.BenchmarkLauncher;
import com.project.benchmark.algorithm.endpoints.Endpoints;
import com.project.benchmark.algorithm.exception.BenchmarkExecutionException;
import com.project.benchmark.algorithm.exception.BenchmarkInitializationException;
import com.project.benchmark.algorithm.internal.BenchmarkConfiguration;
import com.project.benchmark.algorithm.internal.ResponseTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.benchmark.app.config.properties.CoreProperties;
import org.project.benchmark.app.dto.TestProgressDTO;
import org.project.benchmark.app.entity.*;
import org.project.benchmark.app.repository.ResponseRepository;
import org.project.benchmark.app.repository.TestRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityNotFoundException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

import static org.project.benchmark.app.entity.TestStatus.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class BenchmarkService {
    private final Map<Long, BenchmarkLauncher> benchmarks = new ConcurrentHashMap<>();
    private final Map<Long, LinkedBlockingQueue<ResponseTO>> queues = new ConcurrentHashMap<>();
    private final Map<Long, TestStatus> benchmarkStatuses = new ConcurrentHashMap<>();

    private final TestRepository testRepository;
    private final ObjectMapper mapper;
    private final ResponseRepository responseRepository;
    private final CoreProperties coreProperties;

    @PostConstruct
    void init() {
        if (!coreProperties.getAlgorithmThreads().isValid()) {
            throw new IllegalStateException("Property algorithm-threads.min must be lower or equal tha algorithm-threads.max");
        }
        Endpoints.address = coreProperties.getStockBackendAddress();
    }

    @PreDestroy
    void preDestroy() {
        benchmarks.values().forEach(BenchmarkLauncher::forceStop);
        queues.forEach(this::saveSingleQueueResponses);
    }

    public List<TestProgressDTO> getTestsProgress() {
        List<TestProgressDTO> tests = benchmarkStatuses.entrySet()
                .stream()
                .map(e -> {
                    var dto = new TestProgressDTO();
                    dto.setId(e.getKey());
                    dto.setProgress(
                            benchmarks.containsKey(e.getKey())
                                    ? benchmarks.get(e.getKey()).getProgress()
                                    : 0.0
                    );
                    dto.setStatus(e.getValue());
                    return dto;
                }).collect(Collectors.toList());
        List<TestProgressDTO> waitingTests = testRepository.findTestsWaitingForStart()
                .stream().map(i -> new TestProgressDTO(i, 0.0, NEW))
                .collect(Collectors.toList());
        tests.addAll(waitingTests);
        return tests;
    }

    @Transactional
    void startBenchmark(Test test) {
        cleanFinishedBenchmarks();
        changeState(test, INIT);
        Configuration conf = test.getConfiguration();
        BenchmarkConfiguration benchmarkConf = mapper.convertValue(conf, BenchmarkConfiguration.class);
        benchmarkConf.setNoOfUsers(test.getUserCount());
        benchmarkConf.setNoOfStocks(test.getStockCount());
        benchmarkConf.setUserThreads(coreProperties.getCoreThreads());
        benchmarkConf.setIterationCount(test.getIterations());
        benchmarkConf.setBackendMinThreads(coreProperties.getAlgorithmThreads().getMin());
        benchmarkConf.setBackendMaxThreads(coreProperties.getAlgorithmThreads().getMax());
        BenchmarkLauncher launcher = new BenchmarkLauncher(benchmarkConf);
        LinkedBlockingQueue<ResponseTO> queue = new LinkedBlockingQueue<>();
        try {
            boolean success = launcher.start(queue);
            if (success) {
                benchmarks.put(test.getId(), launcher);
                queues.put(test.getId(), queue);
                changeState(test, RUNNING);
                log.info("The test nr " + test.getId() + " has successfully started");
            }
        } catch (BenchmarkInitializationException | BenchmarkExecutionException e) {
            log.error("Error starting benchmark", e);
            changeState(test, ERROR);
        }
    }

    private void cleanFinishedBenchmarks() {
        final List<Long> finished = new ArrayList<>();
        benchmarks.forEach((id, b) -> { if(b.isFinished()) finished.add(id); });
        finished.forEach(benchmarks::remove);
    }

    private void changeState(Test test, TestStatus status) {
        test.setStatus(status);
        testRepository.saveAndFlush(test);
        benchmarkStatuses.put(test.getId(), status);
    }

    @Transactional
    public void stopBenchmark(Long testId) {
        if (!queues.containsKey(testId)) {
            throw new EntityNotFoundException("Test isn't running");
        }
        BenchmarkLauncher launcher = benchmarks.get(testId);
        boolean success = launcher.stop();
        if (success) {
            saveSingleQueueResponses(testId, queues.remove(testId));
            try {
                Test test = testRepository.getOne(testId);
                changeState(test, FINISHED);
                log.info("The test nr " + test.getId() + " has successfully ended");
            } catch (EntityNotFoundException ignored) {
            }
        }
    }

    @Scheduled(fixedDelayString = "${benchmark.algorithm.scheduler.save-interval}")
    void schedule() {
        if (!queues.isEmpty()) {
            for (Map.Entry<Long, LinkedBlockingQueue<ResponseTO>> e : queues.entrySet()) {
                Long testId = e.getKey();
                var queue = e.getValue();
                saveSingleQueueResponses(testId, queue);
            }
        }
    }

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void scheduleStartEnd() {
        if (benchmarks.isEmpty()) {
            List<Test> testsToStart = testRepository.findTestsToBegin(OffsetDateTime.now());
            testsToStart.forEach(this::startBenchmark);
        } else {
            List<Test> allTests = testRepository.findAllById(queues.keySet());
            Map<Long, Test> tests = allTests.stream().collect(Collectors.toMap(Test::getId, t -> t));
            for (var e : benchmarks.entrySet()) {
                if(!queues.containsKey(e.getKey())) {
                    continue;
                }
                Test test = tests.get(e.getKey());
                if (test == null) {
                    stopBenchmark(e.getKey());
                } else if (e.getValue().isFinished()) {
                    stopBenchmark(e.getKey());
                }
            }
            List<Test> testsToStart = testRepository.findTestsToBegin(benchmarks.keySet(), OffsetDateTime.now());
            testsToStart.forEach(this::startBenchmark);
        }
    }

    private void saveSingleQueueResponses(Long testId, LinkedBlockingQueue<ResponseTO> queue) {
        List<ResponseTO> benchmarkResponses = new ArrayList<>();
        if (queue != null) {
            queue.drainTo(benchmarkResponses);
        }
        var list = benchmarkResponses.stream()
                .map(r -> buildResponse(r, testId))
                .collect(Collectors.toList());
        responseRepository.saveAll(list);
        benchmarkResponses.clear();
    }

    private Response buildResponse(ResponseTO res, Long testId) {
        Test test = testRepository.getOne(testId);
        Response response = mapper.convertValue(res, Response.class);
        response.setMethodType(MethodType.valueOf(res.getMethodType()));
        response.setTest(test);
        return response;
    }
}
