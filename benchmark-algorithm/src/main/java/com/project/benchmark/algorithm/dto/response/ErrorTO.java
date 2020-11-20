package com.project.benchmark.algorithm.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ErrorTO {
    private String message;
    private Integer status;
    private Map<String, String> errors;
}
