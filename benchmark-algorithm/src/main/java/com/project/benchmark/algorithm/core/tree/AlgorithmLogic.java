package com.project.benchmark.algorithm.core.tree;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.benchmark.algorithm.core.UserIdentity;
import com.project.benchmark.algorithm.core.UserServiceContainer;
import com.project.benchmark.algorithm.dto.base.PageParams;
import com.project.benchmark.algorithm.dto.base.SortParams;
import com.project.benchmark.algorithm.dto.order.NewOrderTO;
import com.project.benchmark.algorithm.dto.order.OrderFiltersTO;
import com.project.benchmark.algorithm.dto.order.OrderTO;
import com.project.benchmark.algorithm.dto.response.ResponseDataTO;
import com.project.benchmark.algorithm.dto.stock.StockFiltersTO;
import com.project.benchmark.algorithm.dto.stock.StockTO;
import com.project.benchmark.algorithm.dto.user.UserDetailsTO;

import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class AlgorithmLogic {
    private static final Integer maxItemsPerPage = 50;
    private static final SecureRandom random = new SecureRandom();

    private AlgorithmLogic() {
    }

    static void getOwnedOrders(UserIdentity identity) {
        UserServiceContainer container = identity.getServiceContainer();
        if (container == null) {
            return;
        }
        OrderFiltersTO filters = new OrderFiltersTO();
        SortParams sort = new SortParams("id", true);
        filters.setActive(true);
        ArrayList<OrderTO> orders = new ArrayList<>();
        identity.getUserCache().setOwnedOrders(orders);
        int i = 0;
        do {
            PageParams params = new PageParams(i, maxItemsPerPage, Collections.singletonList(sort));
            filters.setPageParams(params);
            var response = container.getDetailsService().getOwnedOrders(filters);
            orders.addAll(response.getData());
            if (response.getData().size() < maxItemsPerPage)
                break;
            i++;
        } while (true);
    }

    static void getOwnedStocks(UserIdentity identity) {
        UserServiceContainer container = identity.getServiceContainer();
        if (container == null) {
            return;
        }
        StockFiltersTO filters = new StockFiltersTO();
        SortParams sort = new SortParams("id", true);
        filters.setTag(identity.getTag());
        ArrayList<StockTO> stocks = new ArrayList<>();
        identity.getUserCache().setOwnedStocks(stocks);
        int i = 0;
        do {
            PageParams params = new PageParams(i, maxItemsPerPage, Collections.singletonList(sort));
            filters.setPageParams(params);
            var response = container.getDetailsService().getOwnedStocks(filters);
            stocks.addAll(response.getData());
            if (response.getData().size() < maxItemsPerPage)
                break;
            i++;
        } while (true);
    }

    static void getAllStocks(UserIdentity identity) {
        UserServiceContainer container = identity.getServiceContainer();
        if (container == null) {
            return;
        }
        StockFiltersTO filters = new StockFiltersTO();
        SortParams sort = new SortParams("name", true);
        filters.setTag(identity.getTag());
        int i = 0;
        ArrayList<StockTO> stocks = new ArrayList<>();
        identity.getUserCache().setStocks(stocks);
        do {
            PageParams params = new PageParams(i, maxItemsPerPage, Collections.singletonList(sort));
            filters.setPageParams(params);
            var response = container.getStockService().getStocks(filters);
            stocks.addAll(response.getData());
            if (response.getData().size() < maxItemsPerPage)
                break;
            i++;
        } while (true);
    }

    static void removeOrder(UserIdentity identity) {
        UserServiceContainer container = identity.getServiceContainer();
        if (container == null) {
            return;
        }
        List<OrderTO> orders = identity.getUserCache().getOwnedOrders();
        int index = random.nextInt(orders.size());
        try {
            container.getOrderService().deactivateOrder(orders.get(index).getId());
        } catch (JsonProcessingException ignored) {
        }
    }

    static void createBuyOrder(UserIdentity identity) {
        UserServiceContainer container = identity.getServiceContainer();
        if (container == null) {
            return;
        }
        List<StockTO> stocks = identity.getUserCache().getStocks();
        if (stocks.isEmpty()) {
            return;
        }
        int index = random.nextInt(stocks.size());
        StockTO stock = stocks.get(index);
        var order = createOrder(stock, "BUYING_ORDER");
        try {
            container.getOrderService().createOrder(order);
        } catch (JsonProcessingException ignored) {
        }
    }

    static void createSellOrder(UserIdentity identity) {
        UserServiceContainer container = identity.getServiceContainer();
        if (container == null) {
            return;
        }
        List<StockTO> stocks = identity.getUserCache().getOwnedStocks();
        if (stocks.isEmpty()) {
            return;
        }
        int index = random.nextInt(stocks.size());
        StockTO stock = stocks.get(index);
        var order = createOrder(stock, "SELLING_ORDER");
        try {
            container.getOrderService().createOrder(order);
        } catch (JsonProcessingException ignored) {
        }
    }

    private static NewOrderTO createOrder(StockTO stock, String type) {
        NewOrderTO newOrder = new NewOrderTO();
        long amount = random.nextInt(stock.getAmount().intValue()) + 1;
        newOrder.setAmount(amount);
        newOrder.setRemainingAmount(amount);
        newOrder.setDateClosing(OffsetDateTime.now().plusDays(3));
        newOrder.setDateCreation(OffsetDateTime.now());
        newOrder.setDateExpiration(OffsetDateTime.now().plusDays(3));
        newOrder.setOrderType(type);
        newOrder.setPrice(stock.getCurrentPrice());
        newOrder.setPriceType("EQUAL");
        newOrder.setStock(stock);
        return newOrder;
    }

    static void login(UserIdentity identity) {
        identity.authenticate();
        UserServiceContainer services = identity.getServiceContainer();
        services.getDetailsService().getUserDetails();
    }

    static void logout(UserIdentity identity) {
        identity.logout();
    }

    static boolean limitReached(UserIdentity identity) {
        return identity.getOperations() <= 0;
    }
}
