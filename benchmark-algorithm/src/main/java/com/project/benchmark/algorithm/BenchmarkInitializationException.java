package com.project.benchmark.algorithm;

public class BenchmarkInitializationException extends RuntimeException {
    public BenchmarkInitializationException(String message) {
        super(message);
    }

    public BenchmarkInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
