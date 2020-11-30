package com.project.benchmark.algorithm.core.tree;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
class BenchmarkProbabilityTreeParams {
    private final int loginAllStocks;
    private final int loginOwnedStocks;
    private final int loginUserOrders;
    private final int loginMakeOrder;
    private final int allStocksMakeOrder;
    private final int allStocksEnd;
    private final int ownedStocksMakeOrder;
    private final int ownedStocksEnd;
    private final int userOrdersMakeOrder;
    private final int userOrdersEnd;
    private final int userOrderDeleteOrder;
    private final int makeOrderBuyOrder;
    private final int makeOrderSellOrder;
}
