package com.project.benchmark.algorithm.dto.stock;

import com.project.benchmark.algorithm.dto.base.PageParams;
import com.project.benchmark.algorithm.utils.QueryString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockFiltersTO {
    private String abbreviation;
    private Long amount;
    @QueryString("amount>")
    private Long amountMore;
    @QueryString("amount<")
    private Long amountLess;
    private Double currentPrice;
    @QueryString("currentPrice>")
    private Double currentPriceMore;
    @QueryString("currentPrice<")
    private Double currentPriceLess;
    private String name;
    @QueryString("priceChangeRatio<")
    private Double priceChangeRatioLess;
    @QueryString("priceChangeRatio>")
    private Double priceChangeRatioMore;
    private PageParams pageParams;
    private String tag;
}
