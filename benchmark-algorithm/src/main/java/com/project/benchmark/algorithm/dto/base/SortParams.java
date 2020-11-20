package com.project.benchmark.algorithm.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SortParams {
    private String name;
    private boolean asc;
}
