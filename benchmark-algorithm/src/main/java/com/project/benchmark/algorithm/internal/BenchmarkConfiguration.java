package com.project.benchmark.algorithm.internal;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BenchmarkConfiguration {
    private BigDecimal loginAllStocks;
    private BigDecimal loginOwnedStocks;
    private BigDecimal loginUserOrders;
    private BigDecimal loginMakeOrder;
    private BigDecimal allStocksMakeOrder;
    private BigDecimal allStocksEnd;
    private BigDecimal ownedStocksMakeOrder;
    private BigDecimal ownedStocksEnd;
    private BigDecimal userOrdersMakeOrder;
    private BigDecimal userOrdersEnd;
    private BigDecimal userOrderDeleteOrder;
    private BigDecimal makeOrderBuyOrder;
    private BigDecimal makeOrderSellOrder;
    private BigDecimal noOfOperations;
    private BigDecimal noOfMoney;
    private Integer noOfUsers;
    private Integer noOfStocks;
    private Integer userThreads;
    private Integer backendMinThreads;
    private Integer backendMaxThreads;
    private Integer iterationCount;
}
