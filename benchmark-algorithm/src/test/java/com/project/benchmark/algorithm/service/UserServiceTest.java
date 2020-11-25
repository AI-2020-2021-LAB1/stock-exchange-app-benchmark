package com.project.benchmark.algorithm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.benchmark.algorithm.dto.user.LoginUserTO;
import com.project.benchmark.algorithm.dto.user.RegisterUserTO;
import com.project.benchmark.algorithm.service.UserService;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.Assert.*;

public class UserServiceTest {

    static UserService userService;

    @BeforeClass
    public static void initialize() {
        userService = new UserService(new LinkedBlockingQueue<>());
    }

    @Test
    public void loginTest() {
        LoginUserTO user = new LoginUserTO("MarcinNajman@gmail.pl", "MarcinNajman.gmail.pl1");
        userService.login(user);
    }

    @Test
    public void registerTest() {
        try {
            RegisterUserTO body = new RegisterUserTO();
            body.setEmail("MarcinNajman@gmail.pl");
            body.setPassword("MarcinNajman.gmail.pl1");
            body.setFirstName("Marcinek");
            body.setLastName("Najmanek");
            userService.register(body, null);
        } catch (JsonProcessingException e) {
            fail("Test failed");
        }
    }
}
