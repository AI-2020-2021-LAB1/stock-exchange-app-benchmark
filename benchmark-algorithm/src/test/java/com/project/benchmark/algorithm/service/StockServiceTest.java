package com.project.benchmark.algorithm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.benchmark.algorithm.dto.base.PageParams;
import com.project.benchmark.algorithm.dto.base.SortParams;
import com.project.benchmark.algorithm.dto.response.ResponseTO;
import com.project.benchmark.algorithm.dto.stock.StockFiltersTO;
import com.project.benchmark.algorithm.dto.user.LoginUserTO;
import junit.framework.TestCase;

import java.io.IOException;
import java.util.Collections;

public class StockServiceTest extends TestCase {

    StockService stockService = new StockService();
    UserService userService = new UserService();

    public void testGetStocks() throws IOException {
        LoginUserTO user = new LoginUserTO();
        user.setUsername("aaa@aaa.pl");
        user.setPassword("Adudek@dud3k");
        String auth = userService.login(user).getData();
        assertNotNull(auth);
        StockFiltersTO filters = new StockFiltersTO();
        SortParams sort = new SortParams("name", true);
        PageParams params = new PageParams(0, 20, Collections.singletonList(sort));
        var response = stockService.getStocks(filters, auth);
        assertNull(response.getError());
        assertNotNull(response.getData());
    }
}
