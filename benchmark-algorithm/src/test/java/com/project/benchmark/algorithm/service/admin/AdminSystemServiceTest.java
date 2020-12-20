package com.project.benchmark.algorithm.service.admin;

import com.project.benchmark.algorithm.dto.response.ResponseDataTO;
import com.project.benchmark.algorithm.dto.system.SystemUsageTO;
import com.project.benchmark.algorithm.dto.user.LoginUserTO;
import com.project.benchmark.algorithm.endpoints.Endpoints;
import com.project.benchmark.algorithm.service.BackendCoreService;
import com.project.benchmark.algorithm.service.UserService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;

public class AdminSystemServiceTest {

    UserService userService = new UserService(new LinkedBlockingQueue<>());
    AdminSystemService service;

//    @BeforeClass
    public static void initClass() {
        Endpoints.address = "address";
    }

//    @Before
    public void init() {
        ResponseDataTO<String> res = userService.login(new LoginUserTO("user", "pass"));
        String token = Objects.requireNonNull(res.getData());
        service = new AdminSystemService(token, new LinkedBlockingQueue<>());
    }

//    @Test
    public void test() {
        ResponseDataTO<List<SystemUsageTO>> systemUsage = service.getSystemUsage();
        List<SystemUsageTO> data = systemUsage.getData();
        data.sort(Comparator.comparing(SystemUsageTO::getTimestamp).reversed());
    }
}
