package com.project.benchmark.algorithm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.benchmark.algorithm.dto.user.RegisterUserTO;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;
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

    private static final String loginURL = "http://{host:port}/oauth/token";
    private static final String registerURL = "http://{host:port}/api/register";

    final ObjectMapper mapper = new ObjectMapper();
    final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public void loginRestEasy() {

        Form form1 = new Form().param("username", "adudek@adudek.pl");
        form1.param("password", "Adudek@dud3k");
        form1.param("scope", "any");
        form1.param("grant_type", "password");

        byte[] encodedAuth = createBasicAuthentication();

        Client client = ClientBuilder.newClient();
        ResteasyWebTarget resteasyWebTarget = (ResteasyWebTarget) client
                .target(loginURL);
        Response response = resteasyWebTarget.request()
                .accept(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN)
                .header("Authorization", "Basic " + new String(encodedAuth))
                .header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                .post(Entity.form(form1));
        try {
            String value = response.readEntity(String.class);
            System.out.println(value);
            response.close();
        } finally {
            client.close();
        }
    }

    public void registerRestEasy() throws JsonProcessingException {

        RegisterUserTO body = new RegisterUserTO();
        body.setEmail("MarcinNajman@gmail.pl");
        body.setPassword("MarcinNajman.gmail.pl1");
        body.setFirstName("Marcinek");
        body.setLastName("Najmanek");

        String jsonString = ofJsonRegisterUser(body);

        byte[] encodedAuth = createBasicAuthentication();
        Client client = ClientBuilder.newClient();
        ResteasyWebTarget resteasyWebTarget = (ResteasyWebTarget) client
                .target(registerURL);
        try {
            Response response = resteasyWebTarget.request()
                    .accept(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN)
                    .header("Authorization", "Basic " + new String(encodedAuth))
                    .header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                    .post(Entity.json(jsonString));
            int returnCode = response.getStatus();
            System.out.println("RC = " + returnCode);
            response.close();
        } finally {
            client.close();
        }
    }

    public void login() {

        Map<Object, Object> form = new HashMap<>();
        form.put("username", "username@username.pl");
        form.put("password", "password@dud3k");
        form.put("scope", "any");
        form.put("grant_type", "password");
        byte[] encodedAuth = createBasicAuthentication();
        System.out.println(form);
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("http://{host:port}/oauth/token"))
                .setHeader("Authorization", "Basic " + new String(encodedAuth))
                .setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                .setHeader("Accept", "application/json, text/plain, */*")
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
        body.setEmail("Beata@Tyszkiewicz.pl");
        body.setPassword("Beata@Tyszkiewicz123");
        body.setFirstName("Beata");
        body.setLastName("Tyszkiewicz");
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

    private String ofJsonRegisterUser(RegisterUserTO body) throws JsonProcessingException {
        ObjectMapper mapper1 = new ObjectMapper();
        String jsonString = mapper1.writeValueAsString(body);
        return jsonString;
    }

}
