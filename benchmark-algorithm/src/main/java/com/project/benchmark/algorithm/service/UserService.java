package com.project.benchmark.algorithm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.benchmark.algorithm.dto.response.ResponseTO;
import com.project.benchmark.algorithm.dto.user.LoginUserTO;
import com.project.benchmark.algorithm.dto.user.RegisterUserTO;
import org.apache.http.HttpHeaders;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Duration;
import java.time.Instant;

public class UserService extends BackendCoreService{

    private static final String loginURL = "http://{host:port}/oauth/token";
    private static final String registerURL = "http://{host:port}/api/register";
    public void login(LoginUserTO user) {

        Form form1 = new Form().param("username", user.getUsername());
        form1.param("password", user.getPassword());
        form1.param("scope", "any");
        form1.param("grant_type", "password");

        byte[] encodedAuth = createBasicAuthentication();

        Client client = ClientBuilder.newClient();
        try(Response response = client
                .target(loginURL).request()
                .accept(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + new String(encodedAuth))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED)
                .post(Entity.form(form1))) {
            String value = response.readEntity(String.class);
            System.out.println(value);
        } finally {
            client.close();
        }
    }

    public ResponseTO<RegisterUserTO> register(RegisterUserTO user) throws JsonProcessingException {
        String jsonString = mapper.writeValueAsString(user);

        byte[] encodedAuth = createBasicAuthentication();
        Client client = ClientBuilder.newClient();
        Instant begin = Instant.now();
        try (Response response = client
                .target(registerURL).request()
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + new String(encodedAuth))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .post(Entity.json(jsonString))) {
            Instant end = Instant.now();
            long time = Duration.between(begin, end).toMillis();
            var params = new EndpointParameters(registerURL, time, "POST");
            return resolveData(response, params, RegisterUserTO.class);
        } finally {
            client.close();
        }
    }
}
