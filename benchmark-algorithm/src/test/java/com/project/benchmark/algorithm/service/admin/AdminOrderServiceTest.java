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
    public void setUp() {
        userService = new UserService(new LinkedBlockingQueue<>());
        orderService = new AdminOrderService(login(), new LinkedBlockingQueue<>());
    }

    private String login() {
        LoginUserTO user = new LoginUserTO();
        user.setUsername("benchmark_Aswqhqdudrx_Yuilbfsgpqk@gmail.com");
        user.setPassword("$2a$10$Bt1BJ8eMEmOAZL6lxew9beVB5Qq8qbHZZLWgH1hHpC7g0ZSyriOwC");
        return userService.login(user).getData();
    }

    @Test
    public void createOrder() throws JsonProcessingException {
        NewOrderTO order = createExampleOrder();
        ResponseDataTO<Void> response = orderService.createOrder(order);
        assertEquals(Integer.valueOf(200), response.getParams().getStatus());
    }

    private NewOrderTO createExampleOrder(){
        NewOrderTO newOrder = new NewOrderTO();
        newOrder.setAmount((long) 10);
        newOrder.setDateClosing(null);
        newOrder.setDateCreation(OffsetDateTime.now());
        newOrder.setDateExpiration(OffsetDateTime.now().plusDays(3));
        newOrder.setOrderType("BUYING_ORDER");
        newOrder.setPrice(1.0);
        newOrder.setPriceType("EQUAL");
        newOrder.setRemainingAmount((long)10);
        StockTO stockTO = new StockTO();
        stockTO.setId(63); //istniejący stock
        stockTO.setPriceChangeRatio(0.0);
        stockTO.setName("benchmark_JwO");
        stockTO.setAmount((long)10);
        stockTO.setCurrentPrice(5.05);
        stockTO.setAbbreviation("JwO");
        stockTO.setTag("BENCHMARK");
        newOrder.setStock(stockTO);
        return newOrder;
    }

    @Test
    public void deactivateOrderTest() {
        Integer orderId = 21;
        ResponseDataTO<Void> response = orderService.deactivateOrder(orderId);
        assertNull(response.getError());
        assertEquals(Integer.valueOf(200), response.getParams().getStatus());
    }
}
