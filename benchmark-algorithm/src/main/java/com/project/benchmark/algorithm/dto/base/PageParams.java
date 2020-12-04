package com.project.benchmark.algorithm.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageParams {
    protected final Integer page;
    protected final Integer size;
    protected final List<SortParams> sort;
}
