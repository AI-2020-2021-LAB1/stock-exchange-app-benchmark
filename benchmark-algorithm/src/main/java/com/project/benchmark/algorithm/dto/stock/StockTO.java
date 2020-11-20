package com.project.benchmark.algorithm.dto.stock;

import lombok.Data;

@Data
public class StockTO {
    private String abbreviation;
    private Long amount;
    private Double currentPrice;
    private Integer id;
    private String name;
    private Double priceChangeRatio;
}
