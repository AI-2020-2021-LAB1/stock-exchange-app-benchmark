package com.project.benchmark.algorithm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.benchmark.algorithm.dto.base.PageParams;
import com.project.benchmark.algorithm.dto.response.ErrorTO;
import com.project.benchmark.algorithm.dto.response.ParametersTO;
import com.project.benchmark.algorithm.dto.response.ResponseDataTO;
import com.project.benchmark.algorithm.internal.ResponseTO;
import com.project.benchmark.algorithm.utils.QueryString;
import org.apache.http.HttpStatus;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.util.HttpResponseCodes;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class BackendCoreService {

    private final static String BUSINESS_LOGIC_EXECUTION_TIME_HEADER = "Execution-Time-Business-Logic";

    protected final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    protected final String fullAuth;
    private final LinkedBlockingQueue<ResponseTO> queue;

    public BackendCoreService(String authorization, LinkedBlockingQueue<ResponseTO> queue) {
        fullAuth = "Bearer " + authorization;
        this.queue = queue;
    }

    public BackendCoreService(LinkedBlockingQueue<ResponseTO> queue) {
        fullAuth = "Basic " + new String(createBasicAuthentication());
        this.queue = queue;
    }

    protected <T> ResponseDataTO<T> manageInvocation(String url, String method, TypeReference<T> ref, Function<ResteasyWebTarget, Invocation> func) {
        Client client = ClientBuilder.newClient();
        ResteasyWebTarget target = (ResteasyWebTarget) client.target(url);
        final Invocation inv = func.apply(target);
        Instant begin = Instant.now();
        try (Response response = inv.invoke()) {
            Instant end = Instant.now();//stop measuring time
            long time = Duration.between(begin, end).toMillis();//calculate time
            var params = new EndpointParameters(url, time, method);//additional info
            ResponseDataTO<T> res = resolveData(response, params, ref);//get full data
            try {
                queue.put(buildResponse(res));
            } catch (InterruptedException ignored) {
            }
            return res;
        } catch (IOException e) {
            return null;
        } finally {
            client.close();
        }
    }

    protected <T> ResponseDataTO<T> manageInvocation(String url, String method, Class<T> clazz, Function<ResteasyWebTarget, Invocation> func) {
        return manageInvocation(url, method, clazz, func, null);
    }

    protected <T> ResponseDataTO<T> manageInvocation(String url, String method, Class<T> clazz, Function<ResteasyWebTarget, Invocation> func, String tag) {
        Client client = ClientBuilder.newClient();
        ResteasyWebTarget target = (ResteasyWebTarget) client.target(url);
        if(tag != null) {
            target.queryParam("tag", tag);
        }
        final Invocation inv = func.apply(target);
        Instant begin = Instant.now();
        try (Response response = inv.invoke()) {
            Instant end = Instant.now();//stop measuring time
            long time = Duration.between(begin, end).toMillis();//calculate time
            var params = new EndpointParameters(url, time, method);//additional info
            ResponseDataTO<T> res = resolveData(response, params, clazz);//get full data
            try {
                queue.put(buildResponse(res));
            } catch (InterruptedException ignored) {
            }
            return res;
        } catch (IOException e) {
            return null;
        } finally {
            client.close();
        }
    }

    private ResponseTO buildResponse(ResponseDataTO<?> res) {
        ResponseTO response = new ResponseTO();
        ParametersTO params = res.getParams();
        response.setStatusCode(params.getStatus());
        response.setResponseDate(params.getResponseDate());
        response.setRequestResponseTime(BigDecimal.valueOf(params.getRequestResponseTime()));
        response.setOperationTime(BigDecimal.valueOf(params.getOperationTime()));
        response.setEndpoint(params.getEndpoint());
        response.setMethodType(params.getMethod());
        response.setUsersLoggedIn(0);//TODO: how to calc this?
        return response;
    }

    private byte[] createBasicAuthentication() {
        String auth = "clientId:clientSecret";
        return Base64.getEncoder().encode(auth.getBytes());
    }

    protected final <T> ResponseDataTO<T> resolveData(Response res, EndpointParameters p, TypeReference<T> expectedClazz) throws IOException {
        ResponseDataTO<T> dto = generateResponse(res, p);
        String json = res.readEntity(String.class);
        if (dto.getParams().getStatus().equals(HttpStatus.SC_OK)) {
            dto.setData(resolveData(json, expectedClazz));
            dto.setSuccess(true);
        } else {
            dto.setError(mapper.readValue(json, ErrorTO.class));
            dto.setSuccess(false);
        }
        return dto;
    }

    protected final <T> ResponseDataTO<T> resolveData(Response res, EndpointParameters p, Class<T> expectedClazz) throws JsonProcessingException {
        ResponseDataTO<T> dto = generateResponse(res, p);
        String json = res.readEntity(String.class);
        if (dto.getParams().getStatus().equals(HttpStatus.SC_OK)) {
            if(Void.class.equals(expectedClazz)) {
                dto.setData(null);
            } else {
                dto.setData(mapper.readValue(json, expectedClazz));
            }
            dto.setSuccess(true);
        } else {
            try {
                dto.setSuccess(false);
                dto.setError(mapper.readValue(json, ErrorTO.class));
            } catch (JsonProcessingException e) { //not authorized or access denied or server error (!)
                ErrorTO error = resolveAuthenticationError(res);
                dto.setError(error);
            }
        }
        return dto;
    }

    private ErrorTO resolveAuthenticationError(Response res) {
        ErrorTO error = new ErrorTO();
        error.setStatus(res.getStatus());
        if(res.getStatus() == HttpResponseCodes.SC_UNAUTHORIZED) {
            error.setMessage("Unauthorized");
        } else if (res.getStatus() == HttpResponseCodes.SC_FORBIDDEN) {
            error.setMessage("Access denied");
        } else {
            error.setMessage("Unable to get error");
        }
        return error;
    }

    private <T> ResponseDataTO<T> generateResponse(Response res, EndpointParameters p) {
        ResponseDataTO<T> dto = new ResponseDataTO<>();
        ParametersTO params = new ParametersTO();
        params.setResponseDate(OffsetDateTime.now());
        params.setStatus(res.getStatus());
        params.setRequestResponseTime(p.getRequestResponseTime());
        params.setEndpoint(p.getEndpoint());
        params.setMethod(p.getMethod());
        try {
            String header = res.getHeaderString(BUSINESS_LOGIC_EXECUTION_TIME_HEADER);
            long nanos = Long.parseLong(header);
            params.setOperationTime((double)nanos / 1000000);
        } catch(Exception ignored) {
        }
        dto.setParams(params);
        return dto;
    }

    private <T> T resolveData(String json, TypeReference<T> type) throws IOException {
        JsonNode root = mapper.readTree(json);
        JsonNode content = root.get("content");
        T typeObject;
        //pageable type
        if (content != null && content.isArray()) {
            typeObject = mapper.convertValue(content, type);
        } else {
            //other
            typeObject = mapper.convertValue(root, type);
        }
        return typeObject;
    }

    protected final <T> MultivaluedMap<String, Object> convertToMap(T object, Class<T> clazz) {
        MultivaluedMap<String, Object> map = new MultivaluedHashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if (field.getType().equals(PageParams.class)) {
                    convertPageParams(map, (PageParams) field.get(object));
                    continue;
                }
                String name = field.getName();
                var ann = field.getAnnotation(QueryString.class);
                if (ann != null) {
                    name = ann.value();
                }
                Object o = field.get(object);
                if(o != null) {
                    map.add(name, o);
                }
            } catch (IllegalAccessException ignored) {
            }
        }
        return map;
    }

    private void convertPageParams(MultivaluedMap<String, Object> map, PageParams params) {
        if(params != null) {
            if(params.getPage() != null) {
                map.add("page", params.getPage());
            }
            if(params.getSize() != null) {
                map.add("size", params.getSize());
            }
            if(params.getSort() != null) {
                List<Object> sort = params.getSort().stream()
                        .map(s -> s.getName() + "," + (s.isAsc() ? "asc" : "desc"))
                        .collect(Collectors.toList());
                map.put("sort", sort);
            }
        }
    }

    /**
     * Automatically put single path param to url.
     * Conversion: {name} => value
     *
     * @param url   URL to convert
     * @param name  path param name
     * @param value value inserted to URL
     * @return new URL with filled path param
     */
    protected final String pathParam(String url, String name, String value) {
        String template = String.format("{%s}", name);
        return url.replace(template, value);
    }
}
