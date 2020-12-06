package org.project.benchmark.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.benchmark.algorithm.BenchmarkLauncher;
import com.project.benchmark.algorithm.internal.BenchmarkConfiguration;
import com.project.benchmark.algorithm.internal.ResponseTO;
import lombok.RequiredArgsConstructor;
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
        BenchmarkLauncher launcher = new BenchmarkLauncher(benchmarkConf);
        LinkedBlockingQueue<ResponseTO> queue = new LinkedBlockingQueue<>();
        boolean success = launcher.start(queue);
        if(success) {
            benchmarks.put(test.getId(), launcher);
            queues.put(test.getId(), queue);
        }
    }

    void stopBenchmark(Test test) {
        Long testId = test.getId();
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
        if(benchmarks.isEmpty()) {
            List<Test> testsToStart = testRepository.findTestsToBegin(OffsetDateTime.now());
            testsToStart.forEach(this::startBenchmark);
        } else {
            List<Test> testsToStart = testRepository.findTestsToBegin(benchmarks.keySet(), OffsetDateTime.now());
            testsToStart.forEach(this::startBenchmark);
            List<Test> testsToEnd = testRepository.findTestsToFinish(benchmarks.keySet(), OffsetDateTime.now());
            testsToEnd.forEach(this::stopBenchmark);
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
