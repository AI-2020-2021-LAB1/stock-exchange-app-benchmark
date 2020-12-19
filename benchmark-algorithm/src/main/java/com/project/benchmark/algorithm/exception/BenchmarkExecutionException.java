package com.project.benchmark.algorithm.exception;

public class BenchmarkExecutionException extends RuntimeException{

    public boolean isCriticalError() {
        return criticalError;
    }

    private final boolean criticalError;

    public BenchmarkExecutionException(String message) {
        super(message);
        criticalError = false;
    }

    public BenchmarkExecutionException(String message, boolean criticalError) {
        super(message);
        this.criticalError = criticalError;
    }

    public BenchmarkExecutionException(String message, Throwable cause) {
        super(message, cause);
        criticalError = false;
    }

    public BenchmarkExecutionException(String message, Throwable cause, boolean criticalCase) {
        super(message, cause);
        this.criticalError = criticalCase;
    }
}
