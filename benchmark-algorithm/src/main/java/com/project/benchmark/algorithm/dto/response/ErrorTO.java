package com.project.benchmark.algorithm.dto.response;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorTO {
    private String message;
    private Integer status;
    private JsonNode errors;
}
