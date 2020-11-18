package com.project.benchmark.algorithm.service.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.benchmark.algorithm.dto.response.ResponseTO;
import com.project.benchmark.algorithm.dto.stock.NewStockTO;
import com.project.benchmark.algorithm.endpoints.Endpoints;
import com.project.benchmark.algorithm.service.BackendCoreService;
import com.project.benchmark.algorithm.service.EndpointParameters;
import com.project.benchmark.algorithm.service.StockService;
import org.apache.http.HttpHeaders;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Duration;
import java.time.Instant;

public class AdminStockService extends StockService {
    private static final String STOCK_CREATE = Endpoints.address + Endpoints.API_STOCK;

    public ResponseTO<Void> createStock(NewStockTO data, String authorization) throws JsonProcessingException {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(STOCK_CREATE);
        String json = mapper.writeValueAsString(data);
        //request-response time start
        Instant begin = Instant.now();
        try (Response response = target.request()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authorization)//token
                .post(Entity.json(json))) {
            Instant end = Instant.now();//stop measuring time
            long time = Duration.between(begin, end).toMillis();//calculate time
            var params = new EndpointParameters(STOCK_CREATE, time, "POST");//additional info
            return resolveData(response, params, Void.class);//get full data
        } finally {
            client.close();
        }
    }
}
