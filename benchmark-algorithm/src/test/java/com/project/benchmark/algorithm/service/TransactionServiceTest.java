package com.project.benchmark.algorithm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.benchmark.algorithm.dto.base.PageParams;
import com.project.benchmark.algorithm.dto.base.SortParams;
import com.project.benchmark.algorithm.dto.response.ResponseTO;
import com.project.benchmark.algorithm.dto.transaction.TransactionFiltersTO;
import com.project.benchmark.algorithm.dto.user.LoginUserTO;
import junit.framework.TestCase;

import java.io.IOException;
import java.util.Collections;

public class TransactionServiceTest extends TestCase {

    TransactionService transactionService = new TransactionService();
    UserService userService = new UserService();

    public void testGetTransactions() throws IOException {
        LoginUserTO user = new LoginUserTO();
        user.setUsername("aaa@aaa.pl");
        user.setPassword("Adudek@dud3k");
        String auth = userService.login(user).getData();
        assertNotNull(auth);
        TransactionFiltersTO filters = new TransactionFiltersTO();
        SortParams sort = new SortParams("name", true);
        PageParams params = new PageParams(0, 20, Collections.singletonList(sort));
        var response = transactionService.getTransactions(filters, auth);
        assertNull(response.getError());
        assertNotNull(response.getData());
    }
}