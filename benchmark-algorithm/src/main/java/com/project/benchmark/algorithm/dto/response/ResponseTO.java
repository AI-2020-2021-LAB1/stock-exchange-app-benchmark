package com.project.benchmark.algorithm.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseTO<T> {
    private T data;
    private ErrorTO error;
    private boolean success;
    private ParametersTO params;
}
