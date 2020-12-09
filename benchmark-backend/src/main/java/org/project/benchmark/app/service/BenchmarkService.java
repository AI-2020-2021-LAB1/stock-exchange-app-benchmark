package org.project.benchmark.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.benchmark.algorithm.BenchmarkLauncher;
import com.project.benchmark.algorithm.exception.BenchmarkInitializationException;
import com.project.benchmark.algorithm.internal.BenchmarkConfiguration;
import com.project.benchmark.algorithm.internal.ResponseTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.benchmark.app.entity.Configuration;
import org.project.benchmark.app.entity.MethodType;
import org.project.benchmark.app.entity.Response;
import org.project.benchmark.app.entity.Test;
import org.project.benchmark.app.repository.ResponseRepository;
import org.project.benchmark.app.repository.TestRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import javax.persistence.EntityNotFoundException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BenchmarkService {
    private final Map<Long, BenchmarkLauncher> benchmarks = new ConcurrentHashMap<>();
    private final Map<Long, LinkedBlockingQueue<ResponseTO>> queues = new ConcurrentHashMap<>();

    private final TestRepository testRepository;
    private final ObjectMapper mapper;
    private final ResponseRepository responseRepository;

    @PreDestroy
    void preDestroy() {
        benchmarks.values().forEach(BenchmarkLauncher::forceStop);
        queues.forEach(this::saveSingleQueueResponses);
    }

    void startBenchmark(Test test) {
        Configuration conf = test.getConfiguration();
        BenchmarkConfiguration benchmarkConf = mapper.convertValue(conf, BenchmarkConfiguration.class);
        benchmarkConf.setNoOfUsers(test.getUserCount());
        benchmarkConf.setNoOfStocks(test.getStockCount());
        BenchmarkLauncher launcher = new BenchmarkLauncher(benchmarkConf);
        LinkedBlockingQueue<ResponseTO> queue = new LinkedBlockingQueue<>();
        try {
            boolean success = launcher.start(queue);
            if(success) {
                benchmarks.put(test.getId(), launcher);
                queues.put(test.getId(), queue);
            }
        } catch (BenchmarkInitializationException e) {
            log.error("Error starting benchmark", e);
        }
    }

    void stopBenchmark(Long testId) {
        if(!benchmarks.containsKey(testId) || !queues.containsKey(testId)) {
            throw new EntityNotFoundException("Test isn't running");
        }
        BenchmarkLauncher launcher = benchmarks.get(testId);
        boolean success = launcher.stop();
        if(success) {
            benchmarks.remove(testId);
            queues.remove(testId);
        }
    }

    public Set<Long> getRunningTests() {
        return benchmarks.keySet();
    }

    @Scheduled(fixedDelay = 5000)
    void schedule() {
        if(!queues.isEmpty()) {
            for(Map.Entry<Long, LinkedBlockingQueue<ResponseTO>> e: queues.entrySet()) {
                Long testId = e.getKey();
                var queue = e.getValue();
                saveSingleQueueResponses( testId, queue);
            }
        }
    }

    @Scheduled(fixedDelay = 5000)
    void scheduleStartEnd() {
        if(benchmarks.isEmpty()) {
            List<Test> testsToStart = testRepository.findTestsToBegin(OffsetDateTime.now());
            testsToStart.forEach(this::startBenchmark);
        } else {
            List<Test> allTests = testRepository.findAllById(benchmarks.keySet());
            Map<Long, Test> tests = allTests.stream().collect(Collectors.toMap(Test::getId, t -> t));
            for(var e: benchmarks.entrySet()) {
                Test test = tests.get(e.getKey());
                if(test == null) {
                    stopBenchmark(e.getKey());
                }
                else if(test.getEndDate().isBefore(OffsetDateTime.now())) {
                    stopBenchmark(e.getKey());
                }
            }
            List<Test> testsToStart = testRepository.findTestsToBegin(benchmarks.keySet(), OffsetDateTime.now());
            testsToStart.forEach(this::startBenchmark);
        }
    }

    private void saveSingleQueueResponses(Long testId, LinkedBlockingQueue<ResponseTO> queue) {
        List<ResponseTO> benchmarkResponses = new ArrayList<>();
        queue.drainTo(benchmarkResponses);
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
