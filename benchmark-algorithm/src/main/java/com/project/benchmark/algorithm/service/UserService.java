package com.project.benchmark.algorithm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.benchmark.algorithm.dto.response.ResponseTO;
import com.project.benchmark.algorithm.dto.user.LoginUserResponseTO;
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

public class UserService extends BackendCoreService {

    private static final String loginURL = "http://193.33.111.196:8000/oauth/token";
    private static final String registerURL = "http://193.33.111.196:8000/api/register";

    public ResponseTO<String> login(LoginUserTO user) throws JsonProcessingException {

        Form form1 = new Form().param("username", user.getUsername());
        form1.param("password", user.getPassword());
        form1.param("scope", "any");
        form1.param("grant_type", "password");

        byte[] encodedAuth = createBasicAuthentication();

        Client client = ClientBuilder.newClient();
        Instant begin = Instant.now();
        try (Response response = client
                .target(loginURL).request()
                .accept(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + new String(encodedAuth))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED)
                .post(Entity.form(form1))) {
            Instant end = Instant.now();
            long time = Duration.between(begin, end).toMillis();
            var params = new EndpointParameters(registerURL, time, "POST");
            ResponseTO<LoginUserResponseTO> res = resolveData(response, params, LoginUserResponseTO.class);
            return res.copy(LoginUserResponseTO::getAccessToken);
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
