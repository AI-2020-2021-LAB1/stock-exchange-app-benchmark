package com.project.benchmark.algorithm.service.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.benchmark.algorithm.dto.response.ResponseTO;
import com.project.benchmark.algorithm.dto.stock.NewStockOwnerTO;
import com.project.benchmark.algorithm.dto.stock.NewStockTO;
import com.project.benchmark.algorithm.dto.stock.NewStockUserTO;
import com.project.benchmark.algorithm.dto.user.LoginUserTO;
import com.project.benchmark.algorithm.service.UserService;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

public class AdminStockServiceTest {

    AdminStockService stockService;
    UserService userService;

    @Before
    public void setUp() {
        stockService = new AdminStockService();
        userService = new UserService();
    }

    private String login() throws JsonProcessingException {
        LoginUserTO user = new LoginUserTO();
        user.setUsername("MarcinNajman@gmail.pl");
        user.setPassword("MarcinNajman.gmail.pl1");
        return userService.login(user).getData();
    }

    @Test
    public void createStock() throws JsonProcessingException {
        String auth = login();
        assertNotNull(auth);
        NewStockTO stock = createExampleStock();
        ResponseTO<Void> response = stockService.createStock(stock, auth);
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
        NewStockOwnerTO owner = new NewStockOwnerTO();
        owner.setAmount(123);
        NewStockUserTO user = new NewStockUserTO();
        user.setId(30); //change user id to correct one (between 2 and 30 should work)
        owner.setUser(user);
        newStock.setOwners(Collections.singletonList(owner));
        return newStock;
    }

    @Test
    public void removeStockTest() throws JsonProcessingException {
        String auth = login();
        assertNotNull(auth);
        Integer stockId = 40; //put there correct stock id (for example from previous test)
        ResponseTO<Void> response = stockService.removeStock(stockId, auth);
        assertNull(response.getError());
        assertEquals(Integer.valueOf(200), response.getParams().getStatus());
    }
}
