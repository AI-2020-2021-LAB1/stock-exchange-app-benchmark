package com.project.benchmark.algorithm.core;

import com.project.benchmark.algorithm.dto.user.LoginUserTO;
import com.project.benchmark.algorithm.internal.ResponseTO;
import com.project.benchmark.algorithm.service.UserService;
import com.project.benchmark.algorithm.service.admin.AdminStockService;
import com.project.benchmark.algorithm.service.admin.AdminTagService;
import lombok.Getter;

import java.util.concurrent.LinkedBlockingQueue;

public class AdminIdentity {

    private final String email = "admin@admin.pl";
    private final String password = "Admin!23";
    private final LinkedBlockingQueue<ResponseTO> queue;
    private String authenticationToken;

    @Getter
    private AdminStockService stockService;
    @Getter
    private AdminTagService tagService;

    public AdminIdentity(LinkedBlockingQueue<ResponseTO> queue) {
        this.queue = queue;
    }

    public void authenticate(UserService service) {
        LoginUserTO user = new LoginUserTO();
        user.setUsername(email);
        user.setPassword(password);
        String token = service.login(user).getData();
        authenticationToken = token;
        if (token != null) {
            stockService = new AdminStockService(authenticationToken, queue);
            tagService = new AdminTagService(authenticationToken, queue);
        }
    }
}
