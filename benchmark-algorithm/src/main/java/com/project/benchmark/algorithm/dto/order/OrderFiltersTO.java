package com.project.benchmark.algorithm.dto.order;

import com.project.benchmark.algorithm.dto.base.PageParams;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class OrderFiltersTO {
    private String abbreviation;
    private boolean active;
    private Double amount;
    private Double amountLess;
    private Double amountMore;
    private OffsetDateTime creationDateLess;
    private OffsetDateTime creationDateMore;
    private OffsetDateTime dateClosingLess;
    private OffsetDateTime dateClosingMore;
    private OffsetDateTime dateExpirationLess;
    private OffsetDateTime dateExpirationMore;
    private String name;
    private String orderType;
    private Double price;
    private Double priceLess;
    private Double priceMore;
    private String priceType;
    private PageParams pageParams;
}
