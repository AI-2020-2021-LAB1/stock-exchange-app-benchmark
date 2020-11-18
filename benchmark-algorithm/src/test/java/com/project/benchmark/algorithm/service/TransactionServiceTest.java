package com.project.benchmark.algorithm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.benchmark.algorithm.dto.base.PageParams;
import com.project.benchmark.algorithm.dto.base.SortParams;
import com.project.benchmark.algorithm.dto.response.ResponseTO;
import com.project.benchmark.algorithm.dto.transaction.TransactionFiltersTO;
import com.project.benchmark.algorithm.dto.transaction.TransactionTO;
import com.project.benchmark.algorithm.dto.user.LoginUserTO;
import junit.framework.TestCase;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class TransactionServiceTest extends TestCase {

    TransactionService transactionService = new TransactionService();
    UserService userService = new UserService();

    private String login() throws JsonProcessingException {
        LoginUserTO user = new LoginUserTO();
        user.setUsername("MarcinNajman@gmail.pl");
        user.setPassword("MarcinNajman.gmail.pl1");
        return userService.login(user).getData();
    }

    private ResponseTO<List<TransactionTO>> getTransactions(String auth) throws IOException {
        TransactionFiltersTO filters = new TransactionFiltersTO();
        SortParams sort = new SortParams("name", true);
        PageParams params = new PageParams(0, 20, Collections.singletonList(sort));
        return transactionService.getTransactions(filters, auth);
    }

    public void testGetTransactions() throws IOException {
        String auth = login();
        assertNotNull(auth);
        var response = getTransactions(auth);
        assertNull(response.getError());
        assertNotNull(response.getData());
    }

    public void testGetTransactionById() throws IOException {
        String auth = login();
        assertNotNull(auth);
        int randomTransactionId = getTransactions(auth).getData()
                .stream().mapToInt(TransactionTO::getId).findAny().orElseThrow();
        ResponseTO<TransactionTO> response = transactionService.getTransactionById(randomTransactionId, auth);
        assertNull(response.getError());
        assertNotNull(response.getData());
    }
}