package com.project.benchmark.algorithm.dto.stock;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class StockIndexTO {
    private Double close;
    private Double max;
    private Double min;
    private Double open;
    private OffsetDateTime timestamp;
}
