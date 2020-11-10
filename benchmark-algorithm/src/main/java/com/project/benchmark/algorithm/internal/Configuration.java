package com.project.benchmark.algorithm.internal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Configuration {
    private boolean registration;
    private BigDecimal certaintyLevel;
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
}
