package com.project.benchmark.algorithm.service.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.project.benchmark.algorithm.dto.response.ResponseTO;
import com.project.benchmark.algorithm.dto.stock.NewStockTO;
import com.project.benchmark.algorithm.dto.stock.StockOwnerTO;
import com.project.benchmark.algorithm.dto.stock.StockOwnersFiltersTO;
import com.project.benchmark.algorithm.dto.stock.StockTO;
import com.project.benchmark.algorithm.endpoints.Endpoints;
import com.project.benchmark.algorithm.service.BackendCoreService;
import com.project.benchmark.algorithm.service.EndpointParameters;
import com.project.benchmark.algorithm.service.StockService;
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

public class AdminStockService extends StockService {
    private static final String STOCK_CREATE = Endpoints.address + Endpoints.API_STOCK;
    private static final String STOCK_DELETE = Endpoints.address + Endpoints.API_STOCK + "/{id}";
    private static final String STOCK_OWNED = Endpoints.address + Endpoints.API_STOCK + "/{id}/owner";

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

    public ResponseTO<Void> removeStock(Integer stockId, String authorization) throws JsonProcessingException {
        Client client = ClientBuilder.newClient();
        String url = this.pathParam(STOCK_DELETE, "id", stockId.toString());
        WebTarget target = client.target(url);
        //request-response time start
        Instant begin = Instant.now();
        try (Response response = target.request()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authorization)//token
                .delete()) {
            Instant end = Instant.now();//stop measuring time
            long time = Duration.between(begin, end).toMillis();//calculate time
            var params = new EndpointParameters(url, time, "DELETE");//additional info
            return resolveData(response, params, Void.class);//get full data
        } finally {
            client.close();
        }
    }

    public ResponseTO<List<StockOwnerTO>> getStockOwners(Integer stockId, StockOwnersFiltersTO filters, String authorization) throws IOException {
        Client client = ClientBuilder.newClient();
        String url = this.pathParam(STOCK_OWNED, "id", stockId.toString());

        ResteasyWebTarget target = (ResteasyWebTarget) client.target(url);
        target.queryParams(this.convertToMap(filters, StockOwnersFiltersTO.class));
        //request-response time start
        Instant begin = Instant.now();
        try (Response response = target.request()
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authorization)//token
                .get()) {
            Instant end = Instant.now();//stop measuring time
            long time = Duration.between(begin, end).toMillis();//calculate time
            var params = new EndpointParameters(url, time, "GET");//additional info
            return resolveData(response, params, new TypeReference<>() {});
        } finally {
            client.close();
        }
    }
}
