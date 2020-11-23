package com.project.benchmark.algorithm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.benchmark.algorithm.dto.base.PageParams;
import com.project.benchmark.algorithm.dto.base.SortParams;
import com.project.benchmark.algorithm.dto.response.ResponseDataTO;
import com.project.benchmark.algorithm.dto.transaction.TransactionFiltersTO;
import com.project.benchmark.algorithm.dto.transaction.TransactionTO;
import com.project.benchmark.algorithm.dto.user.LoginUserTO;
import junit.framework.TestCase;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class TransactionServiceTest extends TestCase {

    TransactionService transactionService;
    UserService userService;

    @Override
    public void setUp() {
        userService = new UserService(new LinkedBlockingQueue<>());
        String auth = login();
        assertNotNull(auth);
        transactionService = new TransactionService(auth, new LinkedBlockingQueue<>());
    }

    private String login() {
        LoginUserTO user = new LoginUserTO();
        user.setUsername("MarcinNajman@gmail.pl");
        user.setPassword("MarcinNajman.gmail.pl1");
        return userService.login(user).getData();
    }

    private ResponseDataTO<List<TransactionTO>> getTransactions() {
        TransactionFiltersTO filters = new TransactionFiltersTO();
        SortParams sort = new SortParams("name", true);
        PageParams params = new PageParams(0, 20, Collections.singletonList(sort));
        filters.setPageParams(params);
        return transactionService.getTransactions(filters);
    }

    public void testGetTransactions() {
        var response = getTransactions();
        assertNull(response.getError());
        assertNotNull(response.getData());
    }

    public void testGetTransactionById() {
        int randomTransactionId = getTransactions().getData()
                .stream().mapToInt(TransactionTO::getId).findAny().orElseThrow();
        ResponseDataTO<TransactionTO> response = transactionService.getTransactionById(randomTransactionId);
        assertNull(response.getError());
        assertNotNull(response.getData());
    }
}
