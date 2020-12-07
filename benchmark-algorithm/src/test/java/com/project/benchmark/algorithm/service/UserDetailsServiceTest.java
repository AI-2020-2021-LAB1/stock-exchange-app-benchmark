package com.project.benchmark.algorithm.service;

import com.project.benchmark.algorithm.dto.base.PageParams;
import com.project.benchmark.algorithm.dto.base.SortParams;
import com.project.benchmark.algorithm.dto.order.OrderFiltersTO;
import com.project.benchmark.algorithm.dto.response.ResponseDataTO;
import com.project.benchmark.algorithm.dto.stock.StockFiltersTO;
import com.project.benchmark.algorithm.dto.user.LoginUserTO;
import com.project.benchmark.algorithm.dto.user.UserDetailsTO;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.Assert.*;

public class UserDetailsServiceTest {

    UserService userService;
    UserDetailsService userDetailsService;

    @Before
    public void setUp() {
        userService = new UserService(new LinkedBlockingQueue<>());
        String auth = login();
        assertNotNull(auth);
        userDetailsService = new UserDetailsService(auth, new LinkedBlockingQueue<>());
    }

    private String login() {
        LoginUserTO user = new LoginUserTO();
        user.setUsername("MarcinNajman@gmail.pl");
        user.setPassword("MarcinNajman.gmail.pl1");
        return userService.login(user).getData();
    }

    @Test
    public void getUserDetails() {
        ResponseDataTO<UserDetailsTO> details = userDetailsService.getUserDetails();
        assertNull(details.getError());
        assertNotNull(details.getData());
    }

    @Test
    public void getOwnedOrders() {
        OrderFiltersTO filters = new OrderFiltersTO();
        SortParams sort = new SortParams("id", true);
        PageParams params = new PageParams(0, 20, Collections.singletonList(sort));
        filters.setPageParams(params);
        var orders = userDetailsService.getOwnedOrders(filters);
        assertNull(orders.getError());
        assertNotNull(orders.getData());
    }

    @Test
    public void getOwnedStocks() {
        StockFiltersTO filters = new StockFiltersTO();
        SortParams sort = new SortParams("id", true);
        PageParams params = new PageParams(0, 20, Collections.singletonList(sort));
        filters.setPageParams(params);
        var stocks = userDetailsService.getOwnedStocks(filters);
        assertNull(stocks.getError());
        assertNotNull(stocks.getData());
    }
}
