package com.project.benchmark.algorithm.dto.transaction;

import com.project.benchmark.algorithm.dto.base.PageParams;
import com.project.benchmark.algorithm.utils.QueryString;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class TransactionFiltersTO {
    private String abbreviation;
    private Long amount;
    @QueryString("amount<")
    private Long amountLess;
    @QueryString("amount>")
    private Long amountMore;
    private OffsetDateTime date;
    @QueryString("date<")
    private OffsetDateTime dateLess;
    @QueryString("date>")
    private OffsetDateTime dateMore;
    private String name;
    private PageParams pageParams;
    private Double unitPrice;
    @QueryString("unitPrice<")
    private Double unitPriceLess;
    @QueryString("unitPrice>")
    private Double unitPriceMore;
}