package com.project.benchmark.algorithm.service.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.benchmark.algorithm.dto.base.PageParams;
import com.project.benchmark.algorithm.dto.base.SortParams;
import com.project.benchmark.algorithm.dto.response.ResponseDataTO;
import com.project.benchmark.algorithm.dto.stock.*;
import com.project.benchmark.algorithm.dto.user.LoginUserTO;
import com.project.benchmark.algorithm.service.UserService;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.Assert.*;

public class AdminStockServiceTest {

    AdminStockService stockService;
    UserService userService;

    @Before
    public void setUp() {
        userService = new UserService(new LinkedBlockingQueue<>());
        String token = login();
        assertNotNull(token);
        stockService = new AdminStockService(token, new LinkedBlockingQueue<>());
    }

    private String login() {
        LoginUserTO user = new LoginUserTO();
        user.setUsername("MarcinNajman@gmail.pl");
        user.setPassword("MarcinNajman.gmail.pl1");
        return userService.login(user).getData();
    }

    @Test
    public void createStock() throws JsonProcessingException {
        NewStockTO stock = createExampleStock();
        ResponseDataTO<Void> response = stockService.createStock(stock, null);
        assertNull(response.getError());
        assertEquals(Integer.valueOf(200), response.getParams().getStatus());
    }

    private NewStockTO createExampleStock() {
        NewStockTO newStock = new NewStockTO();
        newStock.setAbbreviation("abc");
        newStock.setAmount(123);
        newStock.setCurrentPrice(1.23);
        newStock.setName("abcMyFunc");
        newStock.setPriceChangeRatio(0.0);
        StockOwnerTO owner = new StockOwnerTO();
        owner.setAmount(123);
        StockUserTO user = new StockUserTO();
        user.setId(30); //change user id to correct one (between 2 and 30 should work)
        owner.setUser(user);
        newStock.setOwners(Collections.singletonList(owner));
        return newStock;
    }

    @Test
    public void removeStockTest() {
        Integer stockId = 40; //put there correct stock id (for example from previous test)
        ResponseDataTO<Void> response = stockService.removeStock(stockId);
        assertNull(response.getError());
        assertEquals(Integer.valueOf(200), response.getParams().getStatus());
    }

    @Test
    public void getStockOwnersTest() throws IOException {
        Integer stockId = 29; //put there correct stock id (for example from previous test)
        StockOwnersFiltersTO filters = new StockOwnersFiltersTO();
        filters.setPageParams(new PageParams(0, 20, Collections.singletonList(new SortParams("email", true))));
        ResponseDataTO<List<StockOwnerTO>> response = stockService.getStockOwners(stockId, filters);
        assertNull(response.getError());
        assertNotNull(response.getData());
    }
}
