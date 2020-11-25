package com.project.benchmark.algorithm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.benchmark.algorithm.dto.base.PageParams;
import com.project.benchmark.algorithm.dto.base.SortParams;
import com.project.benchmark.algorithm.dto.order.OrderFiltersTO;
import com.project.benchmark.algorithm.dto.response.ResponseDataTO;
import com.project.benchmark.algorithm.dto.order.OrderTO;
import com.project.benchmark.algorithm.dto.transaction.TransactionFiltersTO;
import com.project.benchmark.algorithm.dto.transaction.TransactionTO;
import com.project.benchmark.algorithm.dto.user.LoginUserTO;
import junit.framework.TestCase;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class OrderServiceTest extends TestCase {

    UserService userService;
    OrderService orderService;

    @Override
    public void setUp() {
        userService = new UserService(new LinkedBlockingQueue<>());
        String auth = login();
        assertNotNull(auth);
        orderService = new OrderService(auth, new LinkedBlockingQueue<>());
    }

    private String login() {
        LoginUserTO user = new LoginUserTO();
        user.setUsername("MarcinNajman@gmail.pl");
        user.setPassword("MarcinNajman.gmail.pl1");
        return userService.login(user).getData();
    }

    private ResponseDataTO<List<OrderTO>> getOrders() throws IOException {
        OrderFiltersTO filters = new OrderFiltersTO();
        SortParams sort = new SortParams("name", true);
        PageParams params = new PageParams(0, 20, Collections.singletonList(sort));
        filters.setPageParams(params);
        return orderService.getOrders(filters);
    }

    public void testGetOrders() throws IOException {
        var response = getOrders();
        assertNull(response.getError());
        assertNotNull(response.getData());
    }

    public void testGetOrderById() throws IOException {
        int randomOrderId = getOrders().getData()
                .stream().mapToInt(OrderTO::getId).findAny().orElseThrow();
        ResponseDataTO<OrderTO> response = orderService.getOrderById(randomOrderId);
        assertNull(response.getError());
        assertNotNull(response.getData());
    }

    public void testGetOrderTransactions() throws IOException {
        int randomOrderId = getOrders().getData()
                .stream().mapToInt(OrderTO::getId).findAny().orElseThrow();
        ResponseDataTO<List<TransactionTO>> response = orderService.getOrderTransactions(randomOrderId, new TransactionFiltersTO());
        assertNull(response.getError());
        assertNotNull(response.getData());
    }
}
