package com.project.benchmark.algorithm.core.tree;

import com.project.benchmark.algorithm.core.AlgorithmState;
import com.project.benchmark.algorithm.core.UserIdentity;
import com.project.benchmark.algorithm.internal.BenchmarkConfiguration;
import com.project.benchmark.algorithm.utils.Pair;

import java.math.BigDecimal;

public class AlgorithmProbabilityTreeGenerator {

    public ProbabilityTree<UserIdentity> generate(BenchmarkConfiguration conf, AlgorithmState state) {

        BenchmarkProbabilityTreeParams params = generateInternalConfiguration(conf);

        ProbabilityTree<UserIdentity> tree = new ProbabilityTree<>(state);

        var logoutNode = StopNode.<UserIdentity>builder()
                .tree(tree)
                .consumer(this::logout)
                .build();

        var limitNotReachedNode = ProbabilityNode.<UserIdentity>builder()
                .tree(tree)
                .build();

        var limitReachedCheckNode = ConditionNode.<UserIdentity>builder()
                .predicate(this::limitReached)
                .onTrue(logoutNode)
                .onFalse(limitNotReachedNode)
                .build();

        var sellOrderNode = LinearNode.<UserIdentity>builder()
                .tree(tree)
                .consumer(this::createSellOrder)
                .nextNode(limitReachedCheckNode)
                .build();

        var buyOrderNode = LinearNode.<UserIdentity>builder()
                .tree(tree)
                .consumer(this::createBuyOrder)
                .nextNode(limitReachedCheckNode)
                .build();

        var createOrderNode = ProbabilityNode.<UserIdentity>builder()
                .tree(tree)
                .child(new Pair<>(params.getMakeOrderBuyOrder(), buyOrderNode))
                .child(new Pair<>(params.getMakeOrderSellOrder(), sellOrderNode))
                .build();

        var removeOrderNode = LinearNode.<UserIdentity>builder()
                .tree(tree)
                .consumer(this::removeOrder)
                .nextNode(limitReachedCheckNode)
                .build();

        var allStocksNode = ProbabilityNode.<UserIdentity>builder()
                .consumer(this::getAllStocks)
                .tree(tree)
                .child(new Pair<>(params.getAllStocksMakeOrder(), createOrderNode))
                .child(new Pair<>(params.getAllStocksEnd(), limitReachedCheckNode))
                .build();

        var ownedStocksNode = ProbabilityNode.<UserIdentity>builder()
                .consumer(this::getOwnedStocks)
                .tree(tree)
                .child(new Pair<>(params.getOwnedStocksMakeOrder(), createOrderNode))
                .child(new Pair<>(params.getOwnedStocksEnd(), limitReachedCheckNode))
                .build();

        var ownedOrdersNode = ProbabilityNode.<UserIdentity>builder()
                .consumer(this::getOwnedOrders)
                .tree(tree)
                .child(new Pair<>(params.getUserOrdersMakeOrder(), createOrderNode))
                .child(new Pair<>(params.getUserOrdersEnd(), limitReachedCheckNode))
                .child(new Pair<>(params.getUserOrderDeleteOrder(), removeOrderNode))
                .build();

        var loginNode = ProbabilityNode.<UserIdentity>builder()
                .consumer(this::login)
                .tree(tree)
                .child(new Pair<>(params.getLoginAllStocks(), allStocksNode))
                .child(new Pair<>(params.getLoginOwnedStocks(), ownedStocksNode))
                .child(new Pair<>(params.getLoginUserOrders(), ownedOrdersNode))
                .child(new Pair<>(params.getLoginMakeOrder(), createOrderNode))
                .build();

        limitNotReachedNode.getChildren().addAll(loginNode.getChildren());

        tree.root = loginNode;

        return tree;
    }

    private void getOwnedOrders(UserIdentity identity) {
        //TODO: functionality
    }

    private void getOwnedStocks(UserIdentity identity) {
        //TODO: functionality
    }

    private void removeOrder(UserIdentity identity) {
        //TODO: functionality
    }

    private void createBuyOrder(UserIdentity identity) {
        //TODO: functionality
    }

    private void createSellOrder(UserIdentity identity) {
        //TODO: functionality
    }

    private void login(UserIdentity identity) {
        identity.authenticate();
    }

    private void getAllStocks(UserIdentity identity) {
        //TODO: functionality
    }

    private void logout(UserIdentity identity) {
        identity.logout();
    }

    private boolean limitReached(UserIdentity identity) {
        //TODO: functionality
        return true;
    }

    private BenchmarkProbabilityTreeParams generateInternalConfiguration(BenchmarkConfiguration conf) {
        int level = conf.getCertaintyLevel().intValue();
        switch(level) {
            case 1:
                return LEVEL_1_PARAMS;
            case 2:
                return LEVEL_2_PARAMS;
            case 3:
                return LEVEL_3_PARAMS;
            default:
                return generateFromOutsideConfiguration(conf);
        }
    }

    private BenchmarkProbabilityTreeParams generateFromOutsideConfiguration(BenchmarkConfiguration conf) {
        return BenchmarkProbabilityTreeParams.builder()
                .loginAllStocks(conf.getLoginAllStocks().multiply(MULTIPLIER).intValue())
                .loginOwnedStocks(conf.getLoginOwnedStocks().multiply(MULTIPLIER).intValue())
                .loginUserOrders(conf.getLoginUserOrders().multiply(MULTIPLIER).intValue())
                .loginMakeOrder(conf.getLoginMakeOrder().multiply(MULTIPLIER).intValue())
                .allStocksMakeOrder(conf.getAllStocksMakeOrder().multiply(MULTIPLIER).intValue())
                .allStocksEnd(conf.getAllStocksEnd().multiply(MULTIPLIER).intValue())
                .ownedStocksMakeOrder(conf.getOwnedStocksMakeOrder().multiply(MULTIPLIER).intValue())
                .ownedStocksEnd(conf.getOwnedStocksEnd().multiply(MULTIPLIER).intValue())
                .userOrdersMakeOrder(conf.getUserOrdersMakeOrder().multiply(MULTIPLIER).intValue())
                .userOrdersEnd(conf.getUserOrdersEnd().multiply(MULTIPLIER).intValue())
                .userOrderDeleteOrder(conf.getUserOrderDeleteOrder().multiply(MULTIPLIER).intValue())
                .makeOrderBuyOrder(conf.getMakeOrderBuyOrder().multiply(MULTIPLIER).intValue())
                .makeOrderSellOrder(conf.getMakeOrderSellOrder().multiply(MULTIPLIER).intValue())
                .build();
    }

    private static final BigDecimal MULTIPLIER = BigDecimal.valueOf(100);

    private static final BenchmarkProbabilityTreeParams LEVEL_1_PARAMS = BenchmarkProbabilityTreeParams
            .builder()
            .loginAllStocks(33)
            .loginOwnedStocks(33)
            .loginUserOrders(24)
            .loginMakeOrder(10)
            .allStocksMakeOrder(30)
            .allStocksEnd(70)
            .ownedStocksMakeOrder(30)
            .ownedStocksEnd(70)
            .userOrdersMakeOrder(15)
            .userOrdersEnd(70)
            .userOrderDeleteOrder(15)
            .makeOrderBuyOrder(50)
            .makeOrderSellOrder(50)
            .build();

    private static final BenchmarkProbabilityTreeParams LEVEL_2_PARAMS = BenchmarkProbabilityTreeParams
            .builder()
            .loginAllStocks(30)
            .loginOwnedStocks(30)
            .loginUserOrders(20)
            .loginMakeOrder(20)
            .allStocksMakeOrder(50)
            .allStocksEnd(50)
            .ownedStocksMakeOrder(50)
            .ownedStocksEnd(50)
            .userOrdersMakeOrder(25)
            .userOrdersEnd(50)
            .userOrderDeleteOrder(25)
            .makeOrderBuyOrder(50)
            .makeOrderSellOrder(50)
            .build();

    private static final BenchmarkProbabilityTreeParams LEVEL_3_PARAMS = BenchmarkProbabilityTreeParams
            .builder()
            .loginAllStocks(25)
            .loginOwnedStocks(25)
            .loginUserOrders(10)
            .loginMakeOrder(40)
            .allStocksMakeOrder(70)
            .allStocksEnd(30)
            .ownedStocksMakeOrder(70)
            .ownedStocksEnd(30)
            .userOrdersMakeOrder(35)
            .userOrdersEnd(30)
            .userOrderDeleteOrder(35)
            .makeOrderBuyOrder(50)
            .makeOrderSellOrder(50)
            .build();
}
