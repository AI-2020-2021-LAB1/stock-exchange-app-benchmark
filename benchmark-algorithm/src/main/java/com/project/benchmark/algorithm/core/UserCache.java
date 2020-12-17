package com.project.benchmark.algorithm.core;

import com.project.benchmark.algorithm.dto.order.OrderTO;
import com.project.benchmark.algorithm.dto.stock.StockTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserCache {
    private List<StockTO> stocks = new ArrayList<>();
    private List<StockTO> ownedStocks = new ArrayList<>();
    private List<OrderTO> ownedOrders = new ArrayList<>();
}
