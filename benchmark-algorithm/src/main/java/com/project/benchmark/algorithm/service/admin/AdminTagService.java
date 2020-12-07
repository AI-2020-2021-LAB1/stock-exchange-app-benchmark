package com.project.benchmark.algorithm.service.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.benchmark.algorithm.dto.response.ResponseDataTO;
import com.project.benchmark.algorithm.dto.tag.TagTO;
import com.project.benchmark.algorithm.endpoints.Endpoints;
import com.project.benchmark.algorithm.internal.ResponseTO;
import com.project.benchmark.algorithm.service.BackendCoreService;
import org.apache.http.HttpHeaders;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.LinkedBlockingQueue;

public class AdminTagService extends BackendCoreService {

    private static final String DELETE_TAG = Endpoints.address + "/api/tag/{name}";
    private static final String CREATE_TAG = Endpoints.address + "/api/tag";

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

    public ResponseDataTO<Void> createTag(TagTO tag) throws JsonProcessingException {
        String json = mapper.writeValueAsString(tag);
        return manageInvocation(
                CREATE_TAG,
                HttpMethod.POST,
                Void.class,
                target -> target.request()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, fullAuth)
                        .buildPost(Entity.json(json))
        );
    }
}
