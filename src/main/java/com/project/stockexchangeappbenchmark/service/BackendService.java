package com.project.stockexchangeappbenchmark.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.stockexchangeappbenchmark.dto.user.RegisterUserTO;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class BackendService {

    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();
    private final ObjectMapper mapper = new ObjectMapper();

    public void login() {
        Map<Object, Object> form = new HashMap<>();
        form.put("username", "username@username.pl");
        form.put("password", "password@dud3k");
        form.put("scope", "any");
        form.put("grant_type", "password");
        byte[] encodedAuth = createBasicAuthentication();
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("http://{host:port}/oauth/token"))
                .setHeader("Authorization", "Basic " + new String(encodedAuth))
                .setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                .setHeader("Accept","application/json, text/plain, */*")
                .POST(ofFormData(form))
                .build();
        try {
            HttpResponse<String> res = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
            System.out.println(res.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void register() {
        RegisterUserTO body = new RegisterUserTO();
        body.setEmail("username@username.pl");
        body.setPassword("password@dud3k");
        body.setFirstName("Artur");
        body.setLastName("Dudek");
        byte[] encodedAuth = createBasicAuthentication();
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create("{host:port}"))
                    .setHeader("Authorization", "Basic " + new String(encodedAuth))
                    .setHeader("Content-Type", "application/json;charset=UTF-8")
                    .POST(ofJsonBody(body))
                    .build();
            HttpResponse<String> res = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
            System.out.println(res.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private HttpRequest.BodyPublisher ofFormData(Map<Object, Object> data) {
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder
                    .append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8))
                    .append("=")
                    .append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }

    private HttpRequest.BodyPublisher ofJsonBody(Object obj) throws JsonProcessingException {
        return HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(obj));
    }

    private byte[] createBasicAuthentication() {
        String auth = "clientId:clientSecret";
        return Base64.getEncoder().encode(auth.getBytes());
    }
}
