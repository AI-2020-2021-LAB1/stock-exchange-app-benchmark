package com.project.benchmark.algorithm.service.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.benchmark.algorithm.dto.order.NewOrderTO;
import com.project.benchmark.algorithm.dto.response.ResponseTO;
import com.project.benchmark.algorithm.endpoints.Endpoints;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import com.project.benchmark.algorithm.service.EndpointParameters;
import com.project.benchmark.algorithm.service.OrderService;
import org.apache.http.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Duration;
import java.time.Instant;

public class AdminOrderService extends OrderService {
    private static final String ORDER_CREATE = Endpoints.address + Endpoints.API_ORDER;
    private static final String ORDER_DEACTIVATE = Endpoints.address + Endpoints.API_ORDER +"/{id}/deactivation";

    public ResponseTO<Void> createOrder(NewOrderTO data, String authorization) throws JsonProcessingException {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(ORDER_CREATE);
        String json = mapper.writeValueAsString(data);
        //request-response time start
        Instant begin = Instant.now();
        try (Response response = target.request()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authorization)//token
                .post(Entity.json(json))) {
            Instant end = Instant.now();//stop measuring time
            long time = Duration.between(begin, end).toMillis();//calculate time
            var params = new EndpointParameters(ORDER_CREATE, time, "POST");//additional info
            return resolveData(response, params, Void.class);//get full data
        } finally {
            client.close();
        }
    }

    public ResponseTO<Void> deactivateOrder(Integer orderId, String authorization) throws JsonProcessingException {
        Client client = ClientBuilder.newClient();
        String url = this.pathParam(ORDER_DEACTIVATE, "id", orderId.toString());
        WebTarget target = client.target(url);
        //request-response time start
        Instant begin = Instant.now();
        try (Response response = target.request()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authorization)//token
                .delete()) {
            Instant end = Instant.now();//stop measuring time
            long time = Duration.between(begin, end).toMillis();//calculate time
            var params = new EndpointParameters(url, time, "POST");//additional info
            return resolveData(response, params, Void.class);//get full data
        } finally {
            client.close();
        }
    }
}
