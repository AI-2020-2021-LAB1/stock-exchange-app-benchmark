package com.project.benchmark.algorithm.core;

import com.project.benchmark.algorithm.dto.user.LoginUserTO;
import com.project.benchmark.algorithm.internal.ResponseTO;
import com.project.benchmark.algorithm.service.UserDetailsService;
import com.project.benchmark.algorithm.service.UserService;
import com.project.benchmark.algorithm.service.admin.AdminStockService;
import com.project.benchmark.algorithm.service.admin.AdminSystemService;
import com.project.benchmark.algorithm.service.admin.AdminTagService;
import lombok.Getter;

import java.util.concurrent.LinkedBlockingQueue;

public class AdminIdentity {

    private final String email = "admin@admin.pl";
    private final String password = "Admin!23";
    private final LinkedBlockingQueue<ResponseTO> queue;
    private String authenticationToken;

    private final UserService userService;
    @Getter
    private AdminStockService stockService;
    @Getter
    private AdminTagService tagService;
    @Getter
    private AdminSystemService systemService;

    public AdminIdentity(LinkedBlockingQueue<ResponseTO> queue) {
        this.queue = queue;
        userService = new UserService(queue);
    }

    public void authenticate() {
        LoginUserTO user = new LoginUserTO();
        user.setUsername(email);
        user.setPassword(password);
        String token = userService.login(user).getData();
        authenticationToken = token;
        if (token != null) {
            stockService = new AdminStockService(authenticationToken, queue);
            tagService = new AdminTagService(authenticationToken, queue);
            systemService = new AdminSystemService(authenticationToken, queue);
            UserDetailsService detailsService = new UserDetailsService(authenticationToken, queue);
            detailsService.logout();
        }
    }

    public boolean isAuthenticated() {
        return authenticationToken != null;
    }
}
