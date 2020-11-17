package com.project.benchmark.algorithm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.project.benchmark.algorithm.dto.response.ResponseTO;
import com.project.benchmark.algorithm.dto.stock.StockFiltersTO;
import com.project.benchmark.algorithm.dto.stock.StockTO;
import com.project.benchmark.algorithm.endpoints.Endpoints;
import org.apache.http.HttpHeaders;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class StockService extends BackendCoreService {

    private static final String STOCK_GET_ALL = Endpoints.address + Endpoints.API_STOCK;
    private static final String STOCK_SINGLE = Endpoints.address + Endpoints.API_STOCK + "/{id}";

    public ResponseTO<List<StockTO>> getStocks(StockFiltersTO filters, String authorization) throws IOException {
        Client client = ClientBuilder.newClient();
        ResteasyWebTarget target = (ResteasyWebTarget) client.target(STOCK_GET_ALL);
        //generate query params
        var queryParams = convertToMap(filters, StockFiltersTO.class);
        //save params
        target.queryParams(queryParams);
        //request-response time start
        Instant begin = Instant.now();
        try (Response response = target.request()
                .accept(MediaType.APPLICATION_JSON)//return data
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authorization)//token
                .get()) {
            Instant end = Instant.now();//stop measuring time
            long time = Duration.between(begin, end).toMillis();//calculate time
            var params = new EndpointParameters(STOCK_GET_ALL, time, "GET");//additional info
            return resolveData(response, params, new TypeReference<>() {});//get full data
        } finally {
            client.close();
        }
    }

    public ResponseTO<StockTO> getStockById(Integer id, String authorization) throws JsonProcessingException {
        //convert {id} to proper value
        String url = pathParam(STOCK_SINGLE, "id", id.toString());
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        //request-response time start
        Instant begin = Instant.now();
        try (Response response = target.request()
                .accept(MediaType.APPLICATION_JSON)//return data
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authorization)//token
                .get()) {
            Instant end = Instant.now();//stop measuring time
            long time = Duration.between(begin, end).toMillis();//calculate time
            var params = new EndpointParameters(url, time, "GET");//additional info
            return resolveData(response, params, StockTO.class);//get full data
        } finally {
            client.close();
        }
    }
}
