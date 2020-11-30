package com.project.benchmark.algorithm.core;

import java.util.concurrent.atomic.AtomicBoolean;

public class AlgorithmState {
    public final AtomicBoolean stopSignal;

    public AlgorithmState() {
        this.stopSignal = new AtomicBoolean(false);
    }
}
