package com.project.benchmark.algorithm;

import com.project.benchmark.algorithm.core.UserIdentity;
import com.project.benchmark.algorithm.core.tree.AlgorithmProbabilityTreeGenerator;
import com.project.benchmark.algorithm.core.tree.ProbabilityTree;
import com.project.benchmark.algorithm.exception.BenchmarkInitializationException;
import com.project.benchmark.algorithm.internal.BenchmarkConfiguration;
import com.project.benchmark.algorithm.internal.ResponseTO;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BenchmarkLauncher {

    /**
     * full configuration taken from DB
     */
    private final BenchmarkConfiguration conf;
    /**
     * queue where threads should put responses
     */
    private LinkedBlockingQueue<ResponseTO> responseQueue;

    private BenchmarkEnvironment environment;

    private final Lock lock;
    private boolean started = false;

    public BenchmarkLauncher(BenchmarkConfiguration conf) {
        this.conf = conf;
        lock = new ReentrantLock();
    }

    /**
     * Start Benchmark algorithm with configuration provided in constructor
     *
     * @param responseQueueRef - reference to existing queue. Benchmark threads will add there
     *                         new response results
     * @return true if started successfully, false when another user has already started it
     */
    public boolean start(LinkedBlockingQueue<ResponseTO> responseQueueRef) throws BenchmarkInitializationException {
        lock.lock();
        try {
            if (!started) {
                started = true;
                this.responseQueue = responseQueueRef;
                internalStart();
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Request stop to benchmark.
     * All started algorithms will be finished before stop
     *
     * @return true if stopped successfully, false when another user has already stopped it
     */
    public boolean stop() {
        lock.lock();
        try {
            if (started) {
                started = false;
                internalStop();
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Force stop to benchmark.
     * All started algorithms will be stopped just after finishing algorithm step
     *
     * @return true if stopped successfully, false when another user has already stopped it
     */
    public boolean forceStop() {
        lock.lock();
        try {
            if (started) {
                started = false;
                internalForceStop();
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public boolean isFinished() {
        return environment.isFinished();
    }

    public double getProgress() {
        return environment.getProgress();
    }

    private void internalStart() throws BenchmarkInitializationException {
        ProbabilityTree<UserIdentity> tree = new AlgorithmProbabilityTreeGenerator()
                .generate(conf);

        environment = BenchmarkEnvironment.builder(responseQueue)
                .tree(tree)
                .operations(conf.getNoOfOperations().intValue())
                .userCount(conf.getNoOfUsers())
                .stockCount(conf.getNoOfStocks())
                .iterationsCount(conf.getIterationCount())
                .backendThreading(conf.getBackendMinThreads(), conf.getBackendMaxThreads())
                .userThreading(conf.getUserThreads())
                .build();

        environment.start();
    }

    private void internalStop() {
        environment.stop();
    }

    private void internalForceStop() {
        environment.forceStop();
    }
}
