package com.project.benchmark.algorithm.dto.stock;

import com.project.benchmark.algorithm.utils.QueryString;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class StockIndexFiltersTO {
    @QueryString("datetime<")
    private OffsetDateTime datetimeLess;
    @QueryString("datetime>")
    private OffsetDateTime datetimeMore;
    private Integer interval;
}
