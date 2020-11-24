package com.project.benchmark.algorithm.service.admin;

import com.project.benchmark.algorithm.dto.response.ResponseDataTO;
import com.project.benchmark.algorithm.endpoints.Endpoints;
import com.project.benchmark.algorithm.internal.ResponseTO;
import com.project.benchmark.algorithm.service.BackendCoreService;
import org.apache.http.HttpHeaders;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.LinkedBlockingQueue;

public class AdminTagService extends BackendCoreService {

    private static final String DELETE_TAG = Endpoints.address + "/api/tag/{name}";

    public AdminTagService(String authorization, LinkedBlockingQueue<ResponseTO> queue) {
        super(authorization, queue);
    }

    public ResponseDataTO<Void> removeTag(String tag) {
        String url = pathParam(DELETE_TAG, "name", tag);
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
