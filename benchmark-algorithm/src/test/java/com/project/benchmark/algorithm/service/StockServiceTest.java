package com.project.benchmark.algorithm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.benchmark.algorithm.dto.base.PageParams;
import com.project.benchmark.algorithm.dto.base.SortParams;
import com.project.benchmark.algorithm.dto.response.ResponseTO;
import com.project.benchmark.algorithm.dto.stock.StockFiltersTO;
import com.project.benchmark.algorithm.dto.stock.StockTO;
import com.project.benchmark.algorithm.dto.user.LoginUserTO;
import junit.framework.TestCase;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class StockServiceTest extends TestCase {

    StockService stockService = new StockService();
    UserService userService = new UserService();

    private String login() throws JsonProcessingException {
        LoginUserTO user = new LoginUserTO();
        user.setUsername("MarcinNajman@gmail.pl");
        user.setPassword("MarcinNajman.gmail.pl1");
        return userService.login(user).getData();
    }

    private ResponseTO<List<StockTO>> getStocks(String auth) throws IOException {
        StockFiltersTO filters = new StockFiltersTO();
        SortParams sort = new SortParams("name", true);
        PageParams params = new PageParams(0, 20, Collections.singletonList(sort));
        return stockService.getStocks(filters, auth);
    }

    public void testGetStocks() throws IOException {
        String auth = login();
        assertNotNull(auth);
        var response = getStocks(auth);
        assertNull(response.getError());
        assertNotNull(response.getData());
    }

    public void testGetStockById() throws IOException {
        String auth = login();
        assertNotNull(auth);
        int randomStockId = getStocks(auth).getData()
                .stream().mapToInt(StockTO::getId).findAny().orElseThrow();
        ResponseTO<StockTO> response = stockService.getStockById(randomStockId, auth);
        assertNull(response.getError());
        assertNotNull(response.getData());
    }
}
