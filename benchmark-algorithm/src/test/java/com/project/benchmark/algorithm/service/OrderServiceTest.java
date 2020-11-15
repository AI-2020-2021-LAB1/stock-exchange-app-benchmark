package com.project.benchmark.algorithm.service;

import com.project.benchmark.algorithm.dto.base.PageParams;
import com.project.benchmark.algorithm.dto.base.SortParams;
import com.project.benchmark.algorithm.dto.order.OrderFiltersTO;
import com.project.benchmark.algorithm.dto.stock.StockFiltersTO;
import com.project.benchmark.algorithm.dto.user.LoginUserTO;
import junit.framework.TestCase;

import java.io.IOException;
import java.util.Collections;

public class OrderServiceTest extends TestCase {

    OrderService orderService = new OrderService();
    UserService userService = new UserService();

    public void testGetOrders() throws IOException {
        LoginUserTO user = new LoginUserTO();
        user.setUsername("aaa@aaa.pl");
        user.setPassword("Adudek@dud3k");
        String auth = userService.login(user).getData();
        assertNotNull(auth);
        OrderFiltersTO filters = new OrderFiltersTO();
        SortParams sort = new SortParams("name", true);
        PageParams params = new PageParams(0, 20, Collections.singletonList(sort));
        var response = orderService.getOrders(filters, auth);
        assertNull(response.getError());
        assertNotNull(response.getData());
    }
}
