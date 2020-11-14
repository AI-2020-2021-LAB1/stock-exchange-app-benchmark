package com.project.benchmark.algorithm.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class ParametersTO {
    private String method;
    private OffsetDateTime responseDate;
    private Integer status;
    private String endpoint;
    private Long requestResponseTime;
    private Double operationTime;
}
