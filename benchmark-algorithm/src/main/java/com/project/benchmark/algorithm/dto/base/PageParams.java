package com.project.benchmark.algorithm.dto.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PageParams {
    protected final Integer page;
    protected final Integer size;
    protected final List<SortParams> sort;
}
