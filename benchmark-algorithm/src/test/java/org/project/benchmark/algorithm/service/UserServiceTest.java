package org.project.benchmark.algorithm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.benchmark.algorithm.dto.user.LoginUserTO;
import com.project.benchmark.algorithm.dto.user.RegisterUserTO;
import com.project.benchmark.algorithm.service.UserService;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserServiceTest {

    static UserService userService;

    @BeforeClass
    public static void initialize() {
        userService = new UserService();
    }

    @Test
    public void loginTest() throws JsonProcessingException {
        //LoginUserTO user = new LoginUserTO("MarcinNajman@gmail.pl", "MarcinNajman.gmail.pl1");
        LoginUserTO user = new LoginUserTO("admin@admin.pl", "Admin!23");
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
            userService.register(body);
        } catch (JsonProcessingException e) {
            fail("Test failed");
        }
    }
}
