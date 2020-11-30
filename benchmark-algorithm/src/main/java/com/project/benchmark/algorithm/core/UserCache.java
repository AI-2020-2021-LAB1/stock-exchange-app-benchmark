package com.project.benchmark.algorithm.core;

import com.project.benchmark.algorithm.dto.order.OrderTO;
import com.project.benchmark.algorithm.dto.stock.StockTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserCache {
    private List<StockTO> stocks;
    private List<StockTO> ownedStocks;
    private List<OrderTO> ownedOrders;
}
