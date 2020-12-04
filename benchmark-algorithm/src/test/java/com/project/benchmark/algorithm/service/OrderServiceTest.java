package com.project.benchmark.algorithm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.benchmark.algorithm.dto.base.PageParams;
import com.project.benchmark.algorithm.dto.base.SortParams;
import com.project.benchmark.algorithm.dto.order.OrderFiltersTO;
import com.project.benchmark.algorithm.dto.response.ResponseTO;
import com.project.benchmark.algorithm.dto.order.OrderTO;
import com.project.benchmark.algorithm.dto.user.LoginUserTO;
import junit.framework.TestCase;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class OrderServiceTest extends TestCase {

    OrderService orderService = new OrderService();
    UserService userService = new UserService();

    private String login() throws JsonProcessingException {
        LoginUserTO user = new LoginUserTO();
        user.setUsername("MarcinNajman@gmail.pl");
        user.setPassword("MarcinNajman.gmail.pl1");
        return userService.login(user).getData();
    }

    private ResponseTO<List<OrderTO>> getOrders(String auth) throws IOException {
        OrderFiltersTO filters = new OrderFiltersTO();
        SortParams sort = new SortParams("name", true);
        PageParams params = new PageParams(0, 20, Collections.singletonList(sort));
        return orderService.getOrders(filters, auth);
    }

    public void testGetOrders() throws IOException {
        String auth = login();
        assertNotNull(auth);
        var response = getOrders(auth);
        assertNull(response.getError());
        assertNotNull(response.getData());
    }

    public void testGetOrderById() throws IOException {
        String auth = login();
        assertNotNull(auth);
        int randomOrderId = getOrders(auth).getData()
                .stream().mapToInt(OrderTO::getId).findAny().orElseThrow();
        ResponseTO<OrderTO> response = orderService.getOrderById(randomOrderId, auth);
        assertNull(response.getError());
        assertNotNull(response.getData());
    }
}
