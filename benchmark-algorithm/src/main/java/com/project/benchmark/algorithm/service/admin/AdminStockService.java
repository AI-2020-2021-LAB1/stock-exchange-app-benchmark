package com.project.benchmark.algorithm.service.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.project.benchmark.algorithm.dto.response.ResponseDataTO;
import com.project.benchmark.algorithm.dto.stock.NewStockTO;
import com.project.benchmark.algorithm.dto.stock.StockOwnerTO;
import com.project.benchmark.algorithm.dto.stock.StockOwnersFiltersTO;
import com.project.benchmark.algorithm.endpoints.Endpoints;
import com.project.benchmark.algorithm.internal.ResponseTO;
import com.project.benchmark.algorithm.service.BackendCoreService;
import org.apache.http.HttpHeaders;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class AdminStockService extends BackendCoreService {
    private static final String STOCK_CREATE = Endpoints.address + Endpoints.API_STOCK;
    private static final String STOCK_DELETE = Endpoints.address + Endpoints.API_STOCK + "/{id}";
    private static final String STOCK_OWNED = Endpoints.address + Endpoints.API_STOCK + "/{id}/owner";

    public AdminStockService(String authorization, LinkedBlockingQueue<ResponseTO> queue) {
        super(authorization, queue);
    }

    public ResponseDataTO<Void> createStock(NewStockTO data, String tag) throws JsonProcessingException {
        String json = mapper.writeValueAsString(data);

        return manageInvocation(
                STOCK_CREATE,
                HttpMethod.POST,
                Void.class,
                target -> target.request()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, fullAuth)//token
                        .buildPost(Entity.json(json)),
                tag);
    }

    public ResponseDataTO<Void> removeStock(Integer stockId) {
        String url = this.pathParam(STOCK_DELETE, "id", stockId.toString());

        return manageInvocation(
                url,
                HttpMethod.DELETE,
                Void.class,
                target -> target.request()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, fullAuth)//token
                        .buildDelete()
        );
    }

    public ResponseDataTO<List<StockOwnerTO>> getStockOwners(Integer stockId, StockOwnersFiltersTO filters) throws IOException {
        String url = this.pathParam(STOCK_OWNED, "id", stockId.toString());

        return manageInvocation(
                url,
                HttpMethod.GET,
                new TypeReference<>() {
                },
                target -> target
                        .queryParams(this.convertToMap(filters, StockOwnersFiltersTO.class))
                        .request()
                        .accept(MediaType.APPLICATION_JSON_TYPE)
                        .header(HttpHeaders.AUTHORIZATION, fullAuth)//token
                        .buildGet());
    }
}
