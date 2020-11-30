package com.project.benchmark.algorithm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.benchmark.algorithm.dto.user.LoginUserTO;
import com.project.benchmark.algorithm.dto.user.RegisterUserTO;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static org.junit.Assert.fail;

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
            userService.register(body, "BENCHMARK");
        } catch (JsonProcessingException e) {
            fail("Test failed");
        }
    }

    @Test
    public void registerMultipleThreadTest() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 32, 2, TimeUnit.MINUTES, new LinkedBlockingQueue<>());
        List<Future<?>> futures = new ArrayList<>();
        for (int j = 0; j < 5; j++) {
            int i = j;
            futures.add(executor.submit(() -> runRegisterThread(i * 10)));
        }
        futures.forEach(e -> {
            try {
                e.get();
            } catch (InterruptedException | ExecutionException ignored) {
            }
        });
    }

    private void runRegisterThread(int value) {
        System.out.println("Thread with val " + value);
        int bound = value + 10;
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (int j = value; j < bound; j++) {
            int i = j;
            futures.add(CompletableFuture.runAsync(() -> runRegister(i)));
        }
        futures.forEach(e -> {
            try {
                e.get();
            } catch (InterruptedException | ExecutionException ignored) {
                System.out.println("!");
            }
        });
        System.out.println("Thread finished: " + value);
    }

    private void runRegister(int value) {
        try {
            System.out.println("Subthread with val " + value);
            RegisterUserTO body = new RegisterUserTO();
            body.setEmail("abc" + value + "@abc.pl");
            body.setPassword("MarcinNajman.gmail.pl1");
            body.setFirstName("Marcinek");
            body.setLastName("Najmanek");
            var res = userService.register(body, "BENCHMARK");
            System.out.println("Subthread " + value + " finished: " + res.getParams().getStatus());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
