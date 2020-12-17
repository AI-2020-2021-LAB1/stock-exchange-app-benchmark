package com.project.benchmark.algorithm.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.project.benchmark.algorithm.dto.response.ResponseDataTO;
import com.project.benchmark.algorithm.dto.stock.StockFiltersTO;
import com.project.benchmark.algorithm.dto.stock.StockIndexFiltersTO;
import com.project.benchmark.algorithm.dto.stock.StockIndexTO;
import com.project.benchmark.algorithm.dto.stock.StockTO;
import com.project.benchmark.algorithm.endpoints.Endpoints;
import com.project.benchmark.algorithm.internal.ResponseTO;
import org.apache.http.HttpHeaders;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class StockService extends BackendCoreService {

    private static final String STOCK_GET_ALL = Endpoints.address + Endpoints.API_STOCK;
    private static final String STOCK_SINGLE = Endpoints.address + Endpoints.API_STOCK + "/{id}";
    private static final String STOCK_INDEX = Endpoints.address + Endpoints.API_STOCK + "/{id}/index";

    public StockService(String authorization, LinkedBlockingQueue<ResponseTO> queue) {
        super(authorization, queue);
    }

    public ResponseDataTO<List<StockTO>> getStocks(StockFiltersTO filters) {
        var queryParams = convertToMap(filters, StockFiltersTO.class);

        return manageInvocation(
                STOCK_GET_ALL,
                HttpMethod.GET,
                new TypeReference<>() {},
                target -> target.queryParams(queryParams).request()
                        .accept(MediaType.APPLICATION_JSON)//return data
                        .header(HttpHeaders.AUTHORIZATION, fullAuth)//token
                        .buildGet());
    }

    public ResponseDataTO<StockTO> getStockById(Integer id) {
        //convert {id} to proper value
        String url = pathParam(STOCK_SINGLE, "id", id.toString());

        return manageInvocation(
                url,
                STOCK_SINGLE,
                HttpMethod.GET,
                StockTO.class,
                target -> target.request()
                        .accept(MediaType.APPLICATION_JSON)//return data
                        .header(HttpHeaders.AUTHORIZATION, fullAuth)//token
                        .buildGet()
        );
    }
}
