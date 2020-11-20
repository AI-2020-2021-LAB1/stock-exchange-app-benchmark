package com.project.benchmark.algorithm.dto.response;

import lombok.Data;

import java.util.Map;

@Data
public class ErrorTO {
    private String message;
    private Integer status;
    private Map<String, String> errors;
}
