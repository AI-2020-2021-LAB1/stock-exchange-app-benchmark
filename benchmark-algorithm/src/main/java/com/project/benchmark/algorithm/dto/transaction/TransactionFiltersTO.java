package com.project.benchmark.algorithm.dto.transaction;

import com.project.benchmark.algorithm.dto.base.PageParams;
import com.project.benchmark.algorithm.utils.QueryString;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TransactionFiltersTO {
    private String abbreviation;
    private Long amount;
    private Long amountMore;
    private Long amountLess;
    private Date date;
    private String name;
    private PageParams pageParams;
    private Double unitPrice;
    private Double unitPriceMore;
    private Double unitPriceLess;
}