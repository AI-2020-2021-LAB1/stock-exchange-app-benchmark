package com.project.benchmark.algorithm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.benchmark.algorithm.dto.response.ResponseDataTO;
import com.project.benchmark.algorithm.dto.user.LoginUserResponseTO;
import com.project.benchmark.algorithm.dto.user.LoginUserTO;
import com.project.benchmark.algorithm.dto.user.RegisterUserTO;
import com.project.benchmark.algorithm.internal.ResponseTO;
import org.apache.http.HttpHeaders;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.LinkedBlockingQueue;

public class UserService extends BackendCoreService {

    private static final String loginURL = "http://193.33.111.196:8000/oauth/token";
    private static final String registerURL = "http://193.33.111.196:8000/api/register";

    public UserService(LinkedBlockingQueue<ResponseTO> queue) {
        super(queue);
    }

    public ResponseDataTO<String> login(LoginUserTO user) {

        Form form1 = new Form().param("username", user.getUsername());
        form1.param("password", user.getPassword());
        form1.param("scope", "any");
        form1.param("grant_type", "password");

        ResponseDataTO<LoginUserResponseTO> res = manageInvocation(
                loginURL,
                HttpMethod.POST,
                LoginUserResponseTO.class,
                (target) -> target.request()
                        .accept(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN)
                        .header(HttpHeaders.AUTHORIZATION, fullAuth)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED)
                        .buildPost(Entity.form(form1))
                );
        return res.copy(LoginUserResponseTO::getAccessToken);
    }

    public ResponseDataTO<RegisterUserTO> register(RegisterUserTO user) throws JsonProcessingException {
        String jsonString = mapper.writeValueAsString(user);

        return manageInvocation(
                registerURL,
                HttpMethod.POST,
                RegisterUserTO.class,
                (target) -> target.request()
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, fullAuth)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .buildPost(Entity.json(jsonString))
        );
    }
}
