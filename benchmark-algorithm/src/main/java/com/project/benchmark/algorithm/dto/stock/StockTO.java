package com.project.benchmark.algorithm.dto.stock;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockTO {
    private String abbreviation;
    private Long amount;
    private Double currentPrice;
    private Integer id;
    private String name;
    private Double priceChangeRatio;
    private String tag;
}
