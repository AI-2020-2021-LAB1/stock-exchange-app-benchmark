package com.project.benchmark.algorithm.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Pair<T,U> {
    private final T first;
    private final U last;
}
