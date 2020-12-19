package com.project.benchmark.algorithm.core.tree;

import com.project.benchmark.algorithm.core.UserIdentity;
import com.project.benchmark.algorithm.internal.BenchmarkConfiguration;
import com.project.benchmark.algorithm.utils.Pair;

import java.math.BigDecimal;

public class AlgorithmProbabilityTreeGenerator {

    public ProbabilityTree<UserIdentity> generate(BenchmarkConfiguration conf) {

        BenchmarkProbabilityTreeParams params = generateInternalConfiguration(conf);

        ProbabilityTree<UserIdentity> tree = new ProbabilityTree<>();

        var logoutNode = StopNode.<UserIdentity>builder()
                .consumer(AlgorithmLogic::logout)
                .build();

        var afterLoginNode = ProbabilityNode.<UserIdentity>builder()
                .build();

        var limitReachedCheckNode = ConditionNode.<UserIdentity>builder()
                .predicate(AlgorithmLogic::limitReached)
                .onTrue(logoutNode)
                .onFalse(afterLoginNode)
                .build();

        var sellOrderNode = LinearNode.<UserIdentity>builder()
                .consumer(AlgorithmLogic::createSellOrder)
                .nextNode(limitReachedCheckNode)
                .build();

        var buyOrderNode = LinearNode.<UserIdentity>builder()
                .consumer(AlgorithmLogic::createBuyOrder)
                .nextNode(limitReachedCheckNode)
                .build();

        var createOrderNode = ProbabilityNode.<UserIdentity>builder()
                .child(new Pair<>(params.getMakeOrderBuyOrder(), buyOrderNode))
                .child(new Pair<>(params.getMakeOrderSellOrder(), sellOrderNode))
                .build();

        var removeOrderNode = LinearNode.<UserIdentity>builder()
                .consumer(AlgorithmLogic::removeOrder)
                .nextNode(limitReachedCheckNode)
                .build();

        var allStocksNode = ProbabilityNode.<UserIdentity>builder()
                .consumer(AlgorithmLogic::getAllStocks)
                .child(new Pair<>(params.getAllStocksMakeOrder(), createOrderNode))
                .child(new Pair<>(params.getAllStocksEnd(), limitReachedCheckNode))
                .build();

        var ownedStocksNode = ProbabilityNode.<UserIdentity>builder()
                .consumer(AlgorithmLogic::getOwnedStocks)
                .child(new Pair<>(params.getOwnedStocksMakeOrder(), createOrderNode))
                .child(new Pair<>(params.getOwnedStocksEnd(), limitReachedCheckNode))
                .build();

        var ownedOrdersNode = ProbabilityNode.<UserIdentity>builder()
                .consumer(AlgorithmLogic::getOwnedOrders)
                .child(new Pair<>(params.getUserOrdersMakeOrder(), createOrderNode))
                .child(new Pair<>(params.getUserOrdersEnd(), limitReachedCheckNode))
                .child(new Pair<>(params.getUserOrderDeleteOrder(), removeOrderNode))
                .build();

        var loginNode = LinearNode.<UserIdentity>builder()
                .consumer(AlgorithmLogic::login)
                .nextNode(afterLoginNode)
                .build();

        afterLoginNode.getChildren().add(new Pair<>(params.getLoginAllStocks(), allStocksNode));
        afterLoginNode.getChildren().add(new Pair<>(params.getLoginOwnedStocks(), ownedStocksNode));
        afterLoginNode.getChildren().add(new Pair<>(params.getLoginUserOrders(), ownedOrdersNode));
        afterLoginNode.getChildren().add(new Pair<>(params.getLoginMakeOrder(), createOrderNode));
        tree.root = loginNode;

        return tree;
    }

    private BenchmarkProbabilityTreeParams generateInternalConfiguration(BenchmarkConfiguration conf) {
                return generateFromOutsideConfiguration(conf);
    }

    private BenchmarkProbabilityTreeParams generateFromOutsideConfiguration(BenchmarkConfiguration conf) {
        return BenchmarkProbabilityTreeParams.builder()
                .loginAllStocks(conf.getLoginAllStocks().intValue())
                .loginOwnedStocks(conf.getLoginOwnedStocks().intValue())
                .loginUserOrders(conf.getLoginUserOrders().intValue())
                .loginMakeOrder(conf.getLoginMakeOrder().intValue())
                .allStocksMakeOrder(conf.getAllStocksMakeOrder().intValue())
                .allStocksEnd(conf.getAllStocksEnd().intValue())
                .ownedStocksMakeOrder(conf.getOwnedStocksMakeOrder().intValue())
                .ownedStocksEnd(conf.getOwnedStocksEnd().intValue())
                .userOrdersMakeOrder(conf.getUserOrdersMakeOrder().intValue())
                .userOrdersEnd(conf.getUserOrdersEnd().intValue())
                .userOrderDeleteOrder(conf.getUserOrderDeleteOrder().intValue())
                .makeOrderBuyOrder(conf.getMakeOrderBuyOrder().intValue())
                .makeOrderSellOrder(conf.getMakeOrderSellOrder().intValue())
                .build();
    }

    private static final BigDecimal MULTIPLIER = BigDecimal.valueOf(100);


}
