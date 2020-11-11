package com.project.benchmark.algorithm;

import java.sql.Date;
import java.sql.Timestamp;

public class Responses {
    private long id;
    private long testId;
    private String endpoint;
    private int statusCode;
    private String method;
    private Timestamp responseDate;
    private int usersLoggedIn;
    private int requestResponseTime;
    private int operationTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTestId() {
        return testId;
    }

    public void setTestId(long testId) {
        this.testId = testId;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Timestamp getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(Timestamp responseDate) {
        this.responseDate = responseDate;
    }

    public int getUsersLoggedIn() {
        return usersLoggedIn;
    }

    public void setUsersLoggedIn(int usersLoggedIn) {
        this.usersLoggedIn = usersLoggedIn;
    }

    public int getRequestResponseTime() {
        return requestResponseTime;
    }

    public void setRequestResponseTime(int requestResponseTime) {
        this.requestResponseTime = requestResponseTime;
    }

    public int getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(int operationTime) {
        this.operationTime = operationTime;
    }

    public Responses(long id, long testId, String endpoint, int statusCode, String method, Timestamp responseDate, int usersLoggedIn, int requestResponseTime, int operationTime) {
        this.id = id;
        this.testId = testId;
        this.endpoint = endpoint;
        this.statusCode = statusCode;
        this.method = method;
        this.responseDate = responseDate;
        this.usersLoggedIn = usersLoggedIn;
        this.requestResponseTime = requestResponseTime;
        this.operationTime = operationTime;
    }
}
