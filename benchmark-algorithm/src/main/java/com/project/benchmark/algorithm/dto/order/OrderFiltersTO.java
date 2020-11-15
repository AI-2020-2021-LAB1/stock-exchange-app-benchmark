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
    private Double amount;
    private Double amountLess;
    private Double amountMore;
    private Date creationDateLess; //java.util.Date, możliwe do zmiany
    private Date creationDateMore; //java.util.Date, możliwe do zmiany
    private Date dateClosingLess; //java.util.Date, możliwe do zmiany
    private Date dateClosingMore; //java.util.Date, możliwe do zmiany
    private Date dateExpirationLess; //java.util.Date, możliwe do zmiany
    private Date dateExpirationMore; //java.util.Date, możliwe do zmiany
    private String name;
    private String orderType;
    private Double price;
    private Double priceLess;
    private Double priceMore;
    private String priceType;
    private PageParams pageParams;
}
