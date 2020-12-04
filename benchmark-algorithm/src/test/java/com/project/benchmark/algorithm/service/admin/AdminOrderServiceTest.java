package com.project.benchmark.algorithm.service.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.benchmark.algorithm.dto.order.*;
import com.project.benchmark.algorithm.dto.response.ResponseDataTO;
import com.project.benchmark.algorithm.dto.stock.StockTO;
import com.project.benchmark.algorithm.dto.user.LoginUserTO;
import com.project.benchmark.algorithm.service.UserService;
import org.junit.Before;
import org.junit.Test;

import java.time.OffsetDateTime;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.Assert.*;

public class AdminOrderServiceTest {
    AdminOrderService orderService;
    UserService userService;

    @Before
    public void setUp() throws JsonProcessingException {
        userService = new UserService(new LinkedBlockingQueue<>());
        orderService = new AdminOrderService(login(), new LinkedBlockingQueue<>());
    }

    private String login() {
        LoginUserTO user = new LoginUserTO();
        user.setUsername("{userName}");
        user.setPassword("{password}");
        return userService.login(user).getData();
    }

    @Test
    public void createOrder() throws JsonProcessingException {
        NewOrderTO order = createExampleOrder();
        ResponseDataTO<Void> response = orderService.createOrder(order);
        assertNull(response.getError());
        assertEquals(Integer.valueOf(200), response.getParams().getStatus());
    }

    private NewOrderTO createExampleOrder(){
        NewOrderTO newOrder = new NewOrderTO();
        newOrder.setAmount((long) 123);
        newOrder.setDateClosing(OffsetDateTime.now());
        newOrder.setDateCreation(OffsetDateTime.now());
        newOrder.setDateExpiration(OffsetDateTime.now());
        newOrder.setId(5);
        newOrder.setOrderType("BUYING_ORDER");
        newOrder.setPrice(100.0);
        newOrder.setPriceType("EQUAL");
        newOrder.setRemainingAmount((long)23);
        StockTO stockTO = new StockTO();
        stockTO.setAmount((long)100);
        newOrder.setStock(stockTO);
        return newOrder;
    }

    @Test
    public void deactivateOrderTest() {
        Integer orderId = 40;
        ResponseDataTO<Void> response = orderService.deactivateOrder(orderId);
        assertNull(response.getError());
        assertEquals(Integer.valueOf(200), response.getParams().getStatus());
    }
}
