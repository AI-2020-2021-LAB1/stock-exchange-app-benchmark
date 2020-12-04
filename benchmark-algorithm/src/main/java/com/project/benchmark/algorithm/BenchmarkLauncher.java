package com.project.benchmark.algorithm;

import com.project.benchmark.algorithm.internal.Configuration;
import com.project.benchmark.algorithm.internal.ResponseTO;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BenchmarkLauncher {

    private final Configuration conf;
    private LinkedBlockingQueue<ResponseTO> responseQueue;
    private final ThreadPoolExecutor executor;

    public BenchmarkLauncher(Configuration conf) {
        this.conf = conf;
        executor = new ThreadPoolExecutor(4, 16, 2, TimeUnit.MINUTES, new LinkedBlockingQueue<>());
    }

    /**
     * Start Benchmark algorithm with configuration provided in constructor
     * @param responseQueueRef - reference to existing queue. Benchmark threads will add there
     *                         new response results
     */
    public void start(LinkedBlockingQueue<ResponseTO> responseQueueRef) {
        this.responseQueue = responseQueueRef;
        //TODO: run tasks
    }

    /**
     * Request stop to benchmark.
     * All started algorithms will be finished before stop
     */
    void stop() {
        //TODO: stop working when all elements will finish algorithm
    }

    /**
     * Force stop to benchmark.
     * All started algorithms will be stopped just after finishing algorithm step
     */
    void forceStop() {
        //TODO: immediately stop all processes after single algorithm step
    }
}
