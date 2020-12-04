package com.project.benchmark.algorithm.service.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.benchmark.algorithm.dto.order.*;
import com.project.benchmark.algorithm.dto.response.ResponseTO;
import com.project.benchmark.algorithm.dto.stock.StockTO;
import com.project.benchmark.algorithm.dto.user.LoginUserTO;
import com.project.benchmark.algorithm.service.UserService;
import org.junit.Before;
import org.junit.Test;

import java.time.OffsetDateTime;

import static org.junit.Assert.*;

public class AdminOrderServiceTest {
    AdminOrderService orderService;
    UserService userService;

    @Before
    public void setUp() {
        orderService = new AdminOrderService();
        userService = new UserService();
    }

    private String login() throws JsonProcessingException {
        LoginUserTO user = new LoginUserTO();
        user.setUsername("MarcinNajman@gmail.pl");
        user.setPassword("MarcinNajman.gmail.pl1");
        return userService.login(user).getData();
    }

    @Test
    public void createOrder() throws JsonProcessingException {
        String auth = login();
        assertNotNull(auth);
        NewOrderTO order = createExampleOrder();
        ResponseTO<Void> response = orderService.createOrder(order, auth);
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
    public void deactivateOrderTest() throws JsonProcessingException {
        String auth = login();
        assertNotNull(auth);
        Integer orderId = 40;
        ResponseTO<Void> response = orderService.deactivateOrder(orderId, auth);
        assertNull(response.getError());
        assertEquals(Integer.valueOf(200), response.getParams().getStatus());
    }
}
