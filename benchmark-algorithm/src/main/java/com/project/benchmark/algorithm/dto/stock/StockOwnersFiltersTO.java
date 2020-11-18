package com.project.benchmark.algorithm.dto.stock;

import com.project.benchmark.algorithm.dto.base.PageParams;
import com.project.benchmark.algorithm.utils.QueryString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockOwnersFiltersTO {
    private Long amount;
    @QueryString("amount<")
    private Long amountLess;
    @QueryString("amount>")
    private Long amountMore;
    private String email;
    private String firstName;
    private String lastName;
    private Double money;
    @QueryString("money<")
    private Double moneyLess;
    @QueryString("money>")
    private Double moneyMore;
    private PageParams pageParams;
}
