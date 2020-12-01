package com.project.benchmark.algorithm.core;

import java.util.concurrent.atomic.AtomicBoolean;

public class BenchmarkState {
    public final AtomicBoolean stopSignal;
    public final AtomicBoolean forceStopSignal;

    public BenchmarkState() {
        stopSignal = new AtomicBoolean(false);
        this.forceStopSignal = new AtomicBoolean(false);
    }
}
