package com.project.benchmark.algorithm.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.function.Function;

@Getter
@Setter
public class ResponseTO<T> {
    private T data;
    private ErrorTO error;
    private boolean success;
    private ParametersTO params;

    public <U> ResponseTO<U> copy(Function<T, U> mapper) {
        ResponseTO<U> newRes = new ResponseTO<>();
        newRes.setParams(params);
        newRes.setSuccess(success);
        newRes.setError(error);
        newRes.setData(mapper.apply(data));
        return newRes;
    }
}
