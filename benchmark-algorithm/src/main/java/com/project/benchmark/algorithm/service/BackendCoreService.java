package com.project.benchmark.algorithm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.benchmark.algorithm.dto.base.PageParams;
import com.project.benchmark.algorithm.dto.response.ErrorTO;
import com.project.benchmark.algorithm.dto.response.ParametersTO;
import com.project.benchmark.algorithm.dto.response.ResponseTO;
import com.project.benchmark.algorithm.utils.QueryString;
import org.apache.http.HttpStatus;
import org.jboss.resteasy.util.HttpResponseCodes;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BackendCoreService {

    private final static String BUSINESS_LOGIC_EXECUTION_TIME_HEADER = "Execution-Time-Business-Logic";

    protected final ObjectMapper mapper = new ObjectMapper();

    protected final byte[] createBasicAuthentication() {
        String auth = "clientId:clientSecret";
        return Base64.getEncoder().encode(auth.getBytes());
    }

    protected final <T> ResponseTO<T> resolveData(Response res, EndpointParameters p, TypeReference<T> expectedClazz) throws IOException {
        ResponseTO<T> dto = generateResponse(res, p);
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

    protected final <T> ResponseTO<T> resolveData(Response res, EndpointParameters p, Class<T> expectedClazz) throws JsonProcessingException {
        ResponseTO<T> dto = generateResponse(res, p);
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

    private <T> ResponseTO<T> generateResponse(Response res, EndpointParameters p) {
        ResponseTO<T> dto = new ResponseTO<>();
        ParametersTO params = new ParametersTO();
        params.setResponseDate(OffsetDateTime.now());
        params.setStatus(res.getStatus());
        params.setRequestResponseTime(p.getRequestResponseTime());
        params.setEndpoint(p.getEndpoint());
        params.setMethod(p.getMethod());
        try {
            params.setOperationTime(Double.parseDouble(res.getHeaderString(BUSINESS_LOGIC_EXECUTION_TIME_HEADER)));
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
        if(content != null && content.isArray()) {
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
                if (field.getType().equals(PageParams.class)) {
                    convertPageParams(map, (PageParams) field.get(object));
                }
                String name = field.getName();
                var ann = field.getAnnotation(QueryString.class);
                if (ann != null) {
                    name = ann.value();
                }
                Object o = field.get(object);
                map.add(name, o);
            } catch (IllegalAccessException ignored) {
            }
        }
        return map;
    }

    private void convertPageParams(MultivaluedMap<String, Object> map, PageParams params) {
        map.add("page", params.getPage());
        map.add("size", params.getSize());
        List<Object> sort = params.getSort().stream()
                .map(s -> s.getName() + "," + (s.isAsc() ? "asc" : "desc"))
                .collect(Collectors.toList());
        map.put("sort", sort);
    }

    /**
     * Automatically put single path param to url.
     * Conversion: {name} => value
     * @param url URL to convert
     * @param name path param name
     * @param value value inserted to URL
     * @return new URL with filled path param
     */
    protected final String pathParam(String url, String name, String value) {
        String template = String.format("{%s}", name);
        return url.replace(template, value);
    }
}
