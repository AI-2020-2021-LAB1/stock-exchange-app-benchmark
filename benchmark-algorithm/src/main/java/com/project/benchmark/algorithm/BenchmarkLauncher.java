package com.project.benchmark.algorithm;

import com.project.benchmark.algorithm.internal.BenchmarkConfiguration;
import com.project.benchmark.algorithm.internal.ResponseTO;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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
    /**
     * runner for async tasks
     */
    private final ThreadPoolExecutor executor;
    private final Lock lock;
    private boolean started = false;

    public BenchmarkLauncher(BenchmarkConfiguration conf) {
        this.conf = conf;
        lock = new ReentrantLock();
        executor = new ThreadPoolExecutor(4, 16, 2, TimeUnit.MINUTES, new LinkedBlockingQueue<>());
    }

    /**
     * Start Benchmark algorithm with configuration provided in constructor
     *
     * @param responseQueueRef - reference to existing queue. Benchmark threads will add there
     *                         new response results
     * @return true if started successfully, false when another user has already started it
     */
    public boolean start(LinkedBlockingQueue<ResponseTO> responseQueueRef) {
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

    private void internalStart() {
        //TODO: run tasks
    }

    private void internalStop() {
        //TODO: stop working when all elements will finish algorithm
    }

    private void internalForceStop() {
        //TODO: immediately stop all processes after single algorithm step
    }
}
