package com.project.benchmark.algorithm.dto.stock;

import com.project.benchmark.algorithm.dto.base.PageParams;
import com.project.benchmark.algorithm.utils.QueryString;
import lombok.Data;

@Data
public class StockFiltersTO {
    private String abbreviation;
    private Long amount;
    private Long amountMore;
    private Long amountLess;
    private Double currentPrice;
    private Double currentPriceMore;
    private Double currentPriceLess;
    private String name;
    private Double priceChangeRatioLess;
    private Double priceChangeRatioMore;
    private PageParams pageParams;
}
