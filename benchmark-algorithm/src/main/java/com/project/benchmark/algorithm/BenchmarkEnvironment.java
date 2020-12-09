package com.project.benchmark.algorithm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.benchmark.algorithm.core.AdminIdentity;
import com.project.benchmark.algorithm.core.BenchmarkState;
import com.project.benchmark.algorithm.core.UserIdentity;
import com.project.benchmark.algorithm.core.tree.ProbabilityTree;
import com.project.benchmark.algorithm.dto.response.ResponseDataTO;
import com.project.benchmark.algorithm.dto.stock.NewStockTO;
import com.project.benchmark.algorithm.dto.stock.StockOwnerTO;
import com.project.benchmark.algorithm.dto.stock.StockUserTO;
import com.project.benchmark.algorithm.dto.tag.TagTO;
import com.project.benchmark.algorithm.dto.user.RegisterUserTO;
import com.project.benchmark.algorithm.exception.BenchmarkInitializationException;
import com.project.benchmark.algorithm.internal.ResponseTO;
import com.project.benchmark.algorithm.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.IntStream;

class BenchmarkEnvironment {

    private final List<UserIdentity> users = new ArrayList<>();
    private ThreadPoolExecutor backendExecutor;
    private ThreadPoolExecutor userExecutor;
    private final String tag;
    private final AdminIdentity adminIdentity;
    private ProbabilityTree<UserIdentity> tree;
    private final BenchmarkState state;

    private BenchmarkEnvironment(String tag, AdminIdentity adminIdentity) {
        this.tag = tag;
        this.adminIdentity = adminIdentity;
        this.state = new BenchmarkState();
    }

    static BenchmarkEnvironmentBuilder builder(LinkedBlockingQueue<ResponseTO> queue) {
        return new BenchmarkEnvironmentBuilder(queue);
    }

    public void start() {
        IntStream.range(0, userExecutor.getMaximumPoolSize())
                .forEach(i -> userExecutor.execute(() -> startThread(i)));
    }

    private void startThread(int i) {
        int size = users.size() / userExecutor.getMaximumPoolSize();
        int begin = size * i;
        int end = begin + size + 1;
        if(end > users.size()) {
            end = users.size();
        }
        List<UserIdentity> threadUsers = users.subList(begin, end);
        Map<UserIdentity, Future<?>> futures = new HashMap<>();
        for(UserIdentity ident: threadUsers) {
            futures.put(ident, backendExecutor.submit(() -> tree.execute(ident, state)));
        }
        do {
            Map<UserIdentity, Future<?>> newFutures = new HashMap<>();
            for(var e: futures.entrySet()) {
                try {
                    e.getValue().get(50, TimeUnit.MILLISECONDS);
                    if(!state.stopSignal.get()) {
                        newFutures.put(e.getKey(), backendExecutor.submit(() -> tree.execute(e.getKey(), state)));
                    }
                } catch (InterruptedException | ExecutionException | TimeoutException ignored) {
                }
            }
            futures.putAll(newFutures);
        } while (state.stopSignal.get() && futures.values().stream().allMatch(Future::isDone));
    }

    public void stop() {
        state.stopSignal.set(true);
        userExecutor.shutdown();
        while(true) {
            try {
                if (userExecutor.awaitTermination(10, TimeUnit.SECONDS)) break;
            } catch (InterruptedException ignored) {
            }
        }
        removeTag();
    }

    public void forceStop() {
        state.forceStopSignal.set(true);
        userExecutor.shutdown();
        while(true) {
            try {
                if (userExecutor.awaitTermination(10, TimeUnit.SECONDS)) break;
            } catch (InterruptedException ignored) {
            }
        }
        removeTag();
    }

    private void removeTag() {
        adminIdentity.authenticate();
        if (!adminIdentity.isAuthenticated()) {
            throw new IllegalStateException("Cannot stop test. Unable to login to admin account.");
        }
        adminIdentity.getTagService().removeTag(tag);
    }

    static class BenchmarkEnvironmentBuilder {
        private static final String EMAIL_FORMAT = "%s_user%d@benchmark.com";
        private static final int TAG_CREATION_CHANCES = 5;
        private final AdminIdentity adminIdentity;
        private final UserService userService;
        private final LinkedBlockingQueue<ResponseTO> queue;
        private String tag;
        private Integer userCount;
        private Integer stockCount;
        private Integer operations;
        private Integer backendThreadMin;
        private Integer backendThreadMax;
        private Integer userThreads;
        private BenchmarkEnvironment environment;
        private ProbabilityTree<UserIdentity> tree;

        private BenchmarkEnvironmentBuilder(LinkedBlockingQueue<ResponseTO> queue) {
            adminIdentity = new AdminIdentity(queue);
            userService = new UserService(queue);
            this.queue = queue;
        }

        BenchmarkEnvironmentBuilder userCount(int count) {
            userCount = count;
            return this;
        }

        BenchmarkEnvironmentBuilder stockCount(int count) {
            stockCount = count;
            return this;
        }

        BenchmarkEnvironmentBuilder operations(int count) {
            operations = count;
            return this;
        }

        BenchmarkEnvironmentBuilder backendThreading(int min, int max) {
            backendThreadMin = min;
            backendThreadMax = max;
            return this;
        }

        BenchmarkEnvironmentBuilder userThreading(int count) {
            userThreads = count;
            return this;
        }

        BenchmarkEnvironmentBuilder tree(ProbabilityTree<UserIdentity> tree) {
            this.tree = tree;
            return this;
        }

        BenchmarkEnvironment build() throws BenchmarkInitializationException {
            if (userCount == null || userCount <= 0) {
                throw new BenchmarkInitializationException("User count must be positive");
            }
            if(tree == null) {
                throw new BenchmarkInitializationException("Tree not specified");
            }
            if(operations == null || operations <= 0) {
                throw new BenchmarkInitializationException("Operations count must be positive");
            }
            if(stockCount == null || stockCount <= 0) {
                throw new BenchmarkInitializationException("Stock count must be positive");
            }
            return internalBuild();
        }

        private BenchmarkEnvironment internalBuild() throws BenchmarkInitializationException {
            adminIdentity.authenticate();
            if(!adminIdentity.isAuthenticated()) {
                throw new BenchmarkInitializationException("Unable to login as admin");
            }
            createTag();
            environment = new BenchmarkEnvironment(tag, adminIdentity);
            int executorThreads = userThreads > userCount ? userCount : userThreads;
            environment.userExecutor = new ThreadPoolExecutor(executorThreads, executorThreads, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
            int backendInitialThreads = userCount < 16 ? userCount : userCount * 2 / 5;
            if(backendInitialThreads > backendThreadMin) {
                backendInitialThreads = backendThreadMin;
            }
            int backendMaxThreads = userCount < 16 ? userCount : userCount * 4 / 5;
            if(backendMaxThreads > backendThreadMax) {
                backendInitialThreads = backendThreadMax;
            }
            environment.backendExecutor = new ThreadPoolExecutor(backendInitialThreads, backendMaxThreads, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>());
            environment.tree = tree;
            createUsers();
            createStocks();
            return environment;
        }

        private void createTag() throws BenchmarkInitializationException {
            ResponseDataTO<Void> response;
            for(int counter = 1; counter <= TAG_CREATION_CHANCES; counter++) {
                tag = RandomStringUtils.randomAlphanumeric(15);
                try {
                    response = adminIdentity.getTagService().createTag(new TagTO(tag));
                    if(response.getError() == null) {
                        return;
                    }
                } catch (JsonProcessingException ignored) {
                }
            }
            throw new BenchmarkInitializationException("Unable to create tag");
        }

        private void createUsers() {
            int bound = userCount + 1;
            Map<String, Future<?>> futures = new HashMap<>();
            for (int i = 1; i < bound; i++) {
                RegisterUserTO u = generateUser(i);
                futures.put(u.getEmail(), environment.backendExecutor.submit(() -> userService.register(u, tag)));
            }
            for (var future : futures.entrySet()) {
                try {
                    future.getValue().get();
                    UserIdentity identity = new UserIdentity(future.getKey(), queue, operations, tag);
                    environment.users.add(identity);
                } catch (InterruptedException | ExecutionException ignored) {
                }
            }
        }

        private RegisterUserTO generateUser(int iter) {
            RegisterUserTO user = new RegisterUserTO();
            user.setPassword(UserIdentity.GLOBAL_PASSWORD);
            user.setEmail(String.format(EMAIL_FORMAT, tag, iter));
            user.setFirstName(RandomStringUtils.randomAlphabetic(5, 10));
            user.setLastName("Benchmark");
            return user;
        }

        private void createStocks() {
            int bound = stockCount + 1;
            List<Future<?>> futures = new ArrayList<>();
            for (int i = 1; i < bound; i++) {
                int temp = i;
                futures.add(environment.backendExecutor.submit(() -> {
                    Integer userId = getUserId(temp);
                    if (userId != null) {
                        NewStockTO stock = generateStock(userId);
                        try {
                            adminIdentity.getStockService().createStock(stock, tag);
                        } catch (JsonProcessingException ignored) {
                        }
                    }
                }));
            }
            for (var future : futures) {
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException ignored) {
                }
            }
        }

        private Integer getUserId(int iter) {
            UserIdentity identity = environment.users.get(iter % environment.users.size());
            identity.authenticate();
            if(!identity.isAuthenticated()) {
                return null;
            }
            var details = identity.getServiceContainer().getDetailsService().getUserDetails().getData();
            if(details != null) {
                return details.getId();
            }
            return null;
        }

        private NewStockTO generateStock(Integer userId) {
            int stockAmount = RandomUtils.nextInt(10, 10000);
            int stockTotalValue = RandomUtils.nextInt(20000, 100000);
            double stockSingleValue = round((double)stockTotalValue / stockAmount);
            NewStockTO newStock = new NewStockTO();
            newStock.setAbbreviation(RandomStringUtils.randomAlphabetic(3));
            newStock.setAmount(stockAmount);
            newStock.setCurrentPrice(stockSingleValue);
            newStock.setName(String.format("%s_%s", tag, RandomStringUtils.randomAlphanumeric(10)));
            newStock.setPriceChangeRatio(0.0);
            StockOwnerTO owner = new StockOwnerTO();
            owner.setAmount(newStock.getAmount());
            StockUserTO user = new StockUserTO();
            user.setId(userId);
            owner.setUser(user);
            newStock.setOwners(Collections.singletonList(owner));
            return newStock;
        }

        private double round(double value) {
            int multiplier = 100;
            value = value*multiplier;
            value = Math.round(value);
            return value /multiplier;
        }
    }
}
