package com.project.benchmark.algorithm.service.admin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.project.benchmark.algorithm.dto.response.ResponseDataTO;
import com.project.benchmark.algorithm.dto.system.SystemUsageTO;
import com.project.benchmark.algorithm.endpoints.Endpoints;
import com.project.benchmark.algorithm.internal.ResponseTO;
import com.project.benchmark.algorithm.service.BackendCoreService;
import org.apache.http.HttpHeaders;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.MediaType;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class AdminSystemService extends BackendCoreService {

    private final String SYSTEM_DETAILS = Endpoints.address + "/api/system/resources";

    public AdminSystemService(String authorization, LinkedBlockingQueue<ResponseTO> queue) {
        super(authorization, queue);
    }

    public ResponseDataTO<List<SystemUsageTO>> getSystemUsage() {
        return manageInvocation(
                SYSTEM_DETAILS,
                HttpMethod.GET,
                new TypeReference<>() {
                },
                target -> target
//                        .queryParam("datetime>", OffsetDateTime.now().minusDays(1).atZoneSameInstant(ZoneId.of("+00:00")))
                        .request()
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, fullAuth)//token
                        .buildGet()
        );
    }
}
