package com.project.benchmark.algorithm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.project.benchmark.algorithm.dto.response.ResponseTO;
import com.project.benchmark.algorithm.dto.transaction.TransactionFiltersTO;
import com.project.benchmark.algorithm.dto.transaction.TransactionTO;
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

public class TransactionService extends BackendCoreService {

    private static final String TRANSACTIONS_GET_ALL = Endpoints.address + Endpoints.API_TRANSACTION;
    private static final String TRANSACTION_SINGLE = Endpoints.address + Endpoints.API_TRANSACTION + "/{id}";

    public ResponseTO<List<TransactionTO>> getTransactions(TransactionFiltersTO filters, String authorization) throws IOException {
        Client client = ClientBuilder.newClient();
        ResteasyWebTarget target = (ResteasyWebTarget) client.target(TRANSACTIONS_GET_ALL);
        //generate query params
        var queryParams = convertToMap(filters, TransactionFiltersTO.class);
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
            var params = new EndpointParameters(TRANSACTIONS_GET_ALL, time, "GET");//additional info
            return resolveData(response, params, new TypeReference<>() {
            });//get full data
        } finally {
            client.close();
        }
    }

    public ResponseTO<TransactionTO> getTransactionById(Integer id, String authorization) throws JsonProcessingException {
        //convert {id} to proper value
        String url = pathParam(TRANSACTION_SINGLE, "id", id.toString());
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
            return resolveData(response, params, TransactionTO.class);//get full data
        } finally {
            client.close();
        }
    }
}
