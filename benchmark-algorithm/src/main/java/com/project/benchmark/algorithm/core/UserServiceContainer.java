package com.project.benchmark.algorithm.core;

import com.project.benchmark.algorithm.internal.ResponseTO;
import com.project.benchmark.algorithm.service.OrderService;
import com.project.benchmark.algorithm.service.StockService;
import com.project.benchmark.algorithm.service.TransactionService;
import com.project.benchmark.algorithm.service.UserDetailsService;
import lombok.Getter;

import java.util.concurrent.LinkedBlockingQueue;

@Getter
class UserServiceContainer {
    private final UserDetailsService detailsService;
    private final OrderService orderService;
    private final StockService stockService;
    private final TransactionService transactionService;

    public UserServiceContainer(String auth, LinkedBlockingQueue<ResponseTO> queue) {
        detailsService = new UserDetailsService(auth, queue);
        orderService = new OrderService(auth, queue);
        stockService = new StockService(auth, queue);
        transactionService = new TransactionService(auth, queue);
    }
}
