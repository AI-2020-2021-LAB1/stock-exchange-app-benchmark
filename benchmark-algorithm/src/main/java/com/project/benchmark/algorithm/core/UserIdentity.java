package com.project.benchmark.algorithm.core;

import com.project.benchmark.algorithm.dto.user.LoginUserTO;
import com.project.benchmark.algorithm.internal.ResponseTO;
import com.project.benchmark.algorithm.service.UserService;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class UserIdentity {

    public static final String GLOBAL_PASSWORD = "P@ssword3";

    private final String email;
    private final String password;
    private final LinkedBlockingQueue<ResponseTO> queue;
    private final UserService userService;
    private final int initialOperations;
    private int operations;
    private final UserCache userCache;
    private final String tag;
    private AtomicInteger iterationsLeft;

    private String authenticationToken;
    private UserServiceContainer serviceContainer;

    public UserIdentity(String email,
                        LinkedBlockingQueue<ResponseTO> queue,
                        int initialOperations,
                        String tag,
                        int iterationsLeft) {
        this.email = email;
        this.queue = queue;
        this.initialOperations = initialOperations;
        this.tag = tag;
        this.iterationsLeft = new AtomicInteger(iterationsLeft);
        this.password = GLOBAL_PASSWORD;
        userService = new UserService(queue);
        userCache = new UserCache();
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
        operations = initialOperations;
    }

    public boolean isAuthenticated() {
        return authenticationToken != null;
    }

    public void logout() {
        if(isAuthenticated()) {
            serviceContainer.getDetailsService().logout();
        }
        authenticationToken = null;
        serviceContainer = null;
    }

    public UserServiceContainer getServiceContainer() {
        operations--;
        return serviceContainer;
    }

    public boolean shouldDoNextIteration() {
        return iterationsLeft.decrementAndGet() > 0;
    }

    public int getRemainingIterations() {
        return iterationsLeft.get();
    }

    public int getOperations() {
        return operations;
    }

    public UserCache getUserCache() {
        return userCache;
    }

    public String getTag() {
        return tag;
    }
}
