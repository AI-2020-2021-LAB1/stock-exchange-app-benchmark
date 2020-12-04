package com.project.benchmark.algorithm.dto.order;

import com.project.benchmark.algorithm.dto.base.PageParams;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OrderFiltersTO {
    private String abbreviation;
    private boolean active;
    private Long amount;
    @QueryString("amount<")
    private Long amountLess;
    @QueryString("amount>")
    private Long amountMore;
    @QueryString("creationDate<")
    private OffsetDateTime creationDateLess;
    @QueryString("creationDate>")
    private OffsetDateTime creationDateMore;
    @QueryString("dateClosing<")
    private OffsetDateTime dateClosingLess;
    @QueryString("dateClosing>")
    private OffsetDateTime dateClosingMore;
    @QueryString("dateExpiration<")
    private OffsetDateTime dateExpirationLess;
    @QueryString("dateExpiration>")
    private OffsetDateTime dateExpirationMore;
    private String name;
    private String orderType;
    private Double price;
    @QueryString("price<")
    private Double priceLess;
    @QueryString("price>")
    private Double priceMore;
    private String priceType;
    private PageParams pageParams;
}
