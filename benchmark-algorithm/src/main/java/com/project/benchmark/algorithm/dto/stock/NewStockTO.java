package com.project.benchmark.algorithm.dto.stock;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NewStockTO {
    private String abbreviation;
    private Integer amount;
    private Double currentPrice;
    private String name;
    private Double priceChangeRatio;
    private List<StockOwnerTO> owners;
}
