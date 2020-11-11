package org.project.benchmark.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.benchmark.algorithm.internal.ResponseTO;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.project.benchmark.app.entity.Response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;


class ObjectMapperConfigurationTest {

    static ObjectMapper mapper;

    @BeforeAll
    static void setUp() {
        mapper = new ObjectMapperConfiguration().mapper();
    }

    @Test
    void checkCopy() {
        ResponseTO res = randomResponse();
        Response response = mapper.convertValue(res, Response.class);
        assertResponses(res, response);
    }

    private ResponseTO randomResponse() {
        ResponseTO res = new ResponseTO();
        res.setEndpoint(RandomString.make());
        res.setEndpoint(RandomString.make());
        res.setOperationTime(BigDecimal.ONE);
        res.setRequestResponseTime(BigDecimal.ONE);
        res.setResponseDate(OffsetDateTime.now());
        res.setStatusCode(200);
        res.setUsersLoggedIn(100);
        return res;
    }

    private void assertResponses(ResponseTO one, Response two) {
        assertEquals(one.getEndpoint(), two.getEndpoint());
        assertEquals(one.getOperationTime(), two.getOperationTime());
        assertEquals(one.getUsersLoggedIn(), two.getUsersLoggedIn());
        assertEquals(one.getStatusCode(), two.getStatusCode());
        assertEquals(two.getResponseDate().toInstant(), one.getResponseDate().toInstant());
        assertEquals(one.getRequestResponseTime(), two.getRequestResponseTime());
    }
}
