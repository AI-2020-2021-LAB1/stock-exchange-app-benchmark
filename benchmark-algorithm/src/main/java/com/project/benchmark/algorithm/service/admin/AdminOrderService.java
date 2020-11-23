package com.project.benchmark.algorithm.service.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.benchmark.algorithm.dto.order.NewOrderTO;
import com.project.benchmark.algorithm.dto.response.ResponseDataTO;
import com.project.benchmark.algorithm.endpoints.Endpoints;
import com.project.benchmark.algorithm.internal.ResponseTO;
import com.project.benchmark.algorithm.service.BackendCoreService;
import org.apache.http.HttpHeaders;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.LinkedBlockingQueue;

public class AdminOrderService extends BackendCoreService {
    private static final String ORDER_CREATE = Endpoints.address + Endpoints.API_ORDER;
    private static final String ORDER_DEACTIVATE = Endpoints.address + Endpoints.API_ORDER +"/{id}/deactivation";

    public AdminOrderService(String authorization, LinkedBlockingQueue<ResponseTO> queue) {
        super(authorization, queue);
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

    public ResponseDataTO<Void> deactivateOrder(Integer orderId) {
        String url = this.pathParam(ORDER_DEACTIVATE, "id", orderId.toString());
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
}
