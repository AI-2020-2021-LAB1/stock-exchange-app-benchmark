package com.project.benchmark.algorithm.core;

import com.project.benchmark.algorithm.dto.user.LoginUserTO;
import com.project.benchmark.algorithm.internal.ResponseTO;
import com.project.benchmark.algorithm.service.UserService;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;

public class UserIdentity {

    public static final String GLOBAL_PASSWORD = "P@ssword3";

    private final String email;
    private final String password;
    private final LinkedBlockingQueue<ResponseTO> queue;
    private final UserService userService;

    private String authenticationToken;
    private UserServiceContainer serviceContainer;

    public UserIdentity(String email, LinkedBlockingQueue<ResponseTO> queue) {
        this.email = email;
        this.queue = queue;
        this.password = GLOBAL_PASSWORD;
        userService = new UserService(queue);
    }

    public void authenticate() {
        LoginUserTO user = new LoginUserTO();
        user.setUsername(email);
        user.setPassword(password);
        String token = userService.login(user).getData();
        if(!Objects.isNull(token)) {
            authenticationToken = token;
            serviceContainer = new UserServiceContainer(authenticationToken, queue);
        }
    }

    public void logout() {
        authenticationToken = null;
        serviceContainer = null;
    }

    public UserServiceContainer getServiceContainer() {
        return serviceContainer;
    }
}
