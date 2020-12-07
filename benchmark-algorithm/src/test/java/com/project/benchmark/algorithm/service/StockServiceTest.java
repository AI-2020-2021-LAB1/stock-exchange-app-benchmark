package com.project.benchmark.algorithm.service;

import com.project.benchmark.algorithm.dto.base.PageParams;
import com.project.benchmark.algorithm.dto.base.SortParams;
import com.project.benchmark.algorithm.dto.response.ResponseDataTO;
import com.project.benchmark.algorithm.dto.stock.StockFiltersTO;
import com.project.benchmark.algorithm.dto.stock.StockIndexFiltersTO;
import com.project.benchmark.algorithm.dto.stock.StockIndexTO;
import com.project.benchmark.algorithm.dto.stock.StockTO;
import com.project.benchmark.algorithm.dto.user.LoginUserTO;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class StockServiceTest {
    /*

    StockService stockService;
    UserService userService;

    @Before
    public void setUp() {
        userService = new UserService(new LinkedBlockingQueue<>());
        String auth = login();
        assertNotNull(auth);
        stockService = new StockService(auth, new LinkedBlockingQueue<>());
    }

    private String login() {
        LoginUserTO user = new LoginUserTO();
        user.setUsername("MarcinNajman@gmail.pl");
        user.setPassword("MarcinNajman.gmail.pl1");
        return userService.login(user).getData();
    }

    private ResponseDataTO<List<StockTO>> getStocks() {
        StockFiltersTO filters = new StockFiltersTO();
        SortParams sort = new SortParams("name", true);
        PageParams params = new PageParams(0, 100, Collections.singletonList(sort));
        filters.setPageParams(params);
        return stockService.getStocks(filters);
    }

    @Test
    public void testGetStocks() {
        var response = getStocks();
        assertNull(response.getError());
        assertNotNull(response.getData());
    }

    @Test
    public void testGetStockById() {
        int randomStockId = getStocks().getData()
                .stream().mapToInt(StockTO::getId).findAny().orElseThrow();
        ResponseDataTO<StockTO> response = stockService.getStockById(randomStockId);
        assertNull(response.getError());
        assertNotNull(response.getData());
    }

    @Test
    public void testGetStockIndexes() throws IOException {
        int randomStockId = getStocks().getData()
                .stream().mapToInt(StockTO::getId).findAny().orElseThrow();
        ResponseDataTO<List<StockIndexTO>> response = stockService.getStockIndexes(randomStockId, new StockIndexFiltersTO());
        assertNull(response.getError());
        assertNotNull(response.getData());
    }

     */
}
