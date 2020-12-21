package com.project.benchmark.algorithm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.project.benchmark.algorithm.dto.order.NewOrderTO;
import com.project.benchmark.algorithm.dto.order.OrderFiltersTO;
import com.project.benchmark.algorithm.dto.response.ResponseDataTO;
import com.project.benchmark.algorithm.dto.order.OrderTO;
import com.project.benchmark.algorithm.dto.transaction.TransactionFiltersTO;
import com.project.benchmark.algorithm.dto.transaction.TransactionTO;
import com.project.benchmark.algorithm.endpoints.Endpoints;
import com.project.benchmark.algorithm.internal.ResponseTO;
import org.apache.http.HttpHeaders;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class OrderService extends BackendCoreService {

    private static final String ORDER_GET_ALL = Endpoints.address + Endpoints.API_ORDER;
    private static final String ORDER_SINGLE = Endpoints.address + Endpoints.API_ORDER + "/{id}";
    private static final String ORDER_TRANSACTIONS = Endpoints.address + Endpoints.API_ORDER +"/{id}/transactions";
    private static final String ORDER_CREATE = Endpoints.address + Endpoints.API_ORDER;
    private static final String ORDER_DEACTIVATE = Endpoints.address + Endpoints.API_ORDER +"/{id}/deactivation";

    public OrderService(String authorization, LinkedBlockingQueue<ResponseTO> queue) {
        super(authorization, queue);
    }

    public ResponseDataTO<List<OrderTO>> getOrders(OrderFiltersTO filters) {
        var queryParams = convertToMap(filters, OrderFiltersTO.class);
        return manageInvocation(
                ORDER_GET_ALL,
                HttpMethod.GET,
                new TypeReference<>() {},
                target -> target.queryParams(queryParams).request()
                        .accept(MediaType.APPLICATION_JSON)//return data
                        .header(HttpHeaders.AUTHORIZATION, fullAuth)//token
                        .buildGet()
        );
    }

    public ResponseDataTO<OrderTO> getOrderById(Integer id) {
        //convert {id} to proper value
        String url = pathParam(ORDER_SINGLE, "id", id.toString());
        return manageInvocation(
                url,
                ORDER_SINGLE,
                HttpMethod.GET,
                OrderTO.class,
                target -> target.request()
                        .accept(MediaType.APPLICATION_JSON)//return data
                        .header(HttpHeaders.AUTHORIZATION, fullAuth)//token
                        .buildGet()
        );
    }

    public ResponseDataTO<List<TransactionTO>> getOrderTransactions(Integer orderId, TransactionFiltersTO filters) {
        String url = this.pathParam(ORDER_TRANSACTIONS, "id", orderId.toString());
        return manageInvocation(url,
                HttpMethod.GET,
                new TypeReference<>() {},
                target -> target
                        .queryParams(this.convertToMap(filters, TransactionFiltersTO.class))
                        .request()
                        .accept(MediaType.APPLICATION_JSON_TYPE)
                        .header(HttpHeaders.AUTHORIZATION, fullAuth)//token
                        .buildGet()
        );
    }

    public ResponseDataTO<Void> createOrder(NewOrderTO data) throws JsonProcessingException {
        String json = mapper.writeValueAsString(data);

        return manageInvocation(
                ORDER_CREATE,
                HttpMethod.POST,
                Void.class,
                target -> target.request()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, fullAuth)//token
                        .buildPost(Entity.json(json))
        );
    }

    public ResponseDataTO<Void> deactivateOrder(Integer orderId) throws JsonProcessingException {
        String url = this.pathParam(ORDER_DEACTIVATE, "id", orderId.toString());
        String json = mapper.writeValueAsString(orderId);
        return manageInvocation(
                url,
                ORDER_DEACTIVATE,
                HttpMethod.POST,
                Void.class,
                target -> target.request()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, fullAuth)//token
                        .buildPost(Entity.json(json))
        );
    }

}
