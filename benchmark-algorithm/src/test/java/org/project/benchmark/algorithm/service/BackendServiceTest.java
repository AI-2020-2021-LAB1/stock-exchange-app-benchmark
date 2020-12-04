package org.project.benchmark.algorithm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.benchmark.algorithm.service.BackendService;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class BackendServiceTest {

    static BackendService backendService;

    @BeforeClass
    public static void initialize() {
        backendService = new BackendService();
    }

    @Test
    public void loginTest() {
        backendService.loginRestEasy();
    }

    @Test
    public void registerTest() {
        try {
            backendService.registerRestEasy();
        } catch (JsonProcessingException e) {
            fail("Test failed");
        }
    }
}
