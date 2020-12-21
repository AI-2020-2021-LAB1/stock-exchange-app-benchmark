package com.project.benchmark.algorithm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.project.benchmark.algorithm.dto.response.ResponseDataTO;
import com.project.benchmark.algorithm.dto.transaction.TransactionFiltersTO;
import com.project.benchmark.algorithm.dto.transaction.TransactionTO;
import com.project.benchmark.algorithm.endpoints.Endpoints;
import com.project.benchmark.algorithm.internal.ResponseTO;
import org.apache.http.HttpHeaders;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class TransactionService extends BackendCoreService {

    private static final String TRANSACTIONS_GET_ALL = Endpoints.address + Endpoints.API_TRANSACTION;
    private static final String TRANSACTION_SINGLE = Endpoints.address + Endpoints.API_TRANSACTION + "/{id}";

    public TransactionService(String authorization, LinkedBlockingQueue<ResponseTO> queue) {
        super(authorization, queue);
    }

    public ResponseDataTO<List<TransactionTO>> getTransactions(TransactionFiltersTO filters) {
        var queryParams = convertToMap(filters, TransactionFiltersTO.class);
        return manageInvocation(
                TRANSACTIONS_GET_ALL,
                HttpMethod.GET,
                new TypeReference<>() {},
                target -> target.queryParams(queryParams).request()
                        .accept(MediaType.APPLICATION_JSON)//return data
                        .header(HttpHeaders.AUTHORIZATION, fullAuth)//token
                        .buildGet()
        );
    }

    public ResponseDataTO<TransactionTO> getTransactionById(Integer id) {
        String url = pathParam(TRANSACTION_SINGLE, "id", id.toString());

        return manageInvocation(
                url,
                TRANSACTION_SINGLE,
                HttpMethod.GET,
                TransactionTO.class,
                target -> target.request()
                        .accept(MediaType.APPLICATION_JSON)//return data
                        .header(HttpHeaders.AUTHORIZATION, fullAuth)//token
                        .buildGet()
        );
    }
}
