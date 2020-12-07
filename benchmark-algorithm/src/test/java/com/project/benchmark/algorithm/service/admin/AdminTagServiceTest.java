package com.project.benchmark.algorithm.service.admin;

import com.project.benchmark.algorithm.dto.user.LoginUserTO;
import com.project.benchmark.algorithm.internal.ResponseTO;
import com.project.benchmark.algorithm.service.UserDetailsService;
import com.project.benchmark.algorithm.service.UserService;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.Assert.*;

public class AdminTagServiceTest {

    UserService userService;
    AdminTagService adminTagService;

    @Before
    public void setUp() {
        userService = new UserService(new LinkedBlockingQueue<>());
        String auth = login();
        assertNotNull(auth);
        adminTagService = new AdminTagService(auth, new LinkedBlockingQueue<>());
    }

    private String login() {
        LoginUserTO user = new LoginUserTO();
        user.setUsername("MarcinNajman@gmail.pl");
        user.setPassword("MarcinNajman.gmail.pl1");
        return userService.login(user).getData();
    }

    @Test
    public void removeTagTest() {
        String tag = "BENCHMARK";//put existing tag
        var response = adminTagService.removeTag(tag);
        assertNull(response.getError());
        assertEquals(200, response.getParams().getStatus().intValue());
    }
}
