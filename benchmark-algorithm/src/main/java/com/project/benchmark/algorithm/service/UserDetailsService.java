package com.project.benchmark.algorithm.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.project.benchmark.algorithm.dto.order.OrderFiltersTO;
import com.project.benchmark.algorithm.dto.order.OrderTO;
import com.project.benchmark.algorithm.dto.response.ResponseDataTO;
import com.project.benchmark.algorithm.dto.stock.StockFiltersTO;
import com.project.benchmark.algorithm.dto.stock.StockTO;
import com.project.benchmark.algorithm.dto.user.UserDetailsTO;
import com.project.benchmark.algorithm.endpoints.Endpoints;
import com.project.benchmark.algorithm.internal.ResponseTO;
import org.apache.http.HttpHeaders;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class UserDetailsService extends BackendCoreService {

    private static final String USER_DATA = Endpoints.address + "/api/user/config/user-data";
    private static final String OWNED_ORDERS = Endpoints.address + "/api/user/order/owned";
    private static final String OWNED_STOCKS = Endpoints.address + "/api/user/stock/owned";

    public UserDetailsService(String authorization, LinkedBlockingQueue<ResponseTO> queue) {
        super(authorization, queue);
    }

    public ResponseDataTO<UserDetailsTO> getUserDetails() {
        return manageInvocation(
                USER_DATA,
                HttpMethod.GET,
                UserDetailsTO.class,
                target -> target
                        .request()
                        .accept(MediaType.APPLICATION_JSON_TYPE)
                        .header(HttpHeaders.AUTHORIZATION, fullAuth)
                        .buildGet()
        );
    }

    public void logout() {
        loggedInUsers.decrementAndGet();
    }

    public ResponseDataTO<List<OrderTO>> getOwnedOrders(OrderFiltersTO filters) {
        var queryParams = convertToMap(filters, OrderFiltersTO.class);
        return manageInvocation(
                OWNED_ORDERS,
                HttpMethod.GET,
                new TypeReference<>() {
                },
                target -> target
                        .queryParams(queryParams)
                        .request()
                        .accept(MediaType.APPLICATION_JSON)//return data
                        .header(HttpHeaders.AUTHORIZATION, fullAuth)//token
                        .buildGet()
        );
    }

    public ResponseDataTO<List<StockTO>> getOwnedStocks(StockFiltersTO filters) {
        var queryParams = convertToMap(filters, StockFiltersTO.class);
        return manageInvocation(
                OWNED_STOCKS,
                HttpMethod.GET,
                new TypeReference<>() {
                },
                target -> target
                        .queryParams(queryParams)
                        .request()
                        .accept(MediaType.APPLICATION_JSON)//return data
                        .header(HttpHeaders.AUTHORIZATION, fullAuth)//token
                        .buildGet()
        );
    }
}
