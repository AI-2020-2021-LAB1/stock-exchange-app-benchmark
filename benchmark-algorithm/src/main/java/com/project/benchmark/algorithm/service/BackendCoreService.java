package com.project.benchmark.algorithm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.benchmark.algorithm.dto.response.ErrorTO;
import com.project.benchmark.algorithm.dto.response.ParametersTO;
import com.project.benchmark.algorithm.dto.response.ResponseTO;
import com.project.benchmark.algorithm.dto.user.LoginUserTO;
import com.project.benchmark.algorithm.dto.user.RegisterUserTO;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Base64;

public abstract class BackendCoreService {

    private final static String BUSINESS_LOGIC_EXECUTION_TIME_HEADER = "Execution-Time-Business-Logic";

    protected final ObjectMapper mapper = new ObjectMapper();

    protected final byte[] createBasicAuthentication() {
        String auth = "clientId:clientSecret";
        return Base64.getEncoder().encode(auth.getBytes());
    }

    protected final <T> ResponseTO<T> resolveData(Response res, EndpointParameters p, Class<T> expectedClazz) {
        ResponseTO<T> dto = new ResponseTO<>();
        ParametersTO params = new ParametersTO();
        params.setResponseDate(OffsetDateTime.now());
        params.setStatus(res.getStatus());
        params.setRequestResponseTime(p.getRequestResponseTime());
        params.setEndpoint(p.getEndpoint());
        params.setMethod(p.getMethod());
        if(params.getStatus().equals(HttpStatus.SC_OK)) {
            dto.setData(res.readEntity(expectedClazz));
            dto.setSuccess(true);
        }
        else {
            dto.setError(res.readEntity(ErrorTO.class));
            dto.setSuccess(false);
        }
        params.setOperationTime(Double.parseDouble(res.getHeaderString(BUSINESS_LOGIC_EXECUTION_TIME_HEADER)));
        dto.setParams(params);
        return dto;
    }

}
