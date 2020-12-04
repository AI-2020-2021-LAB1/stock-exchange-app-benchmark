package com.project.benchmark.algorithm.dto.response;

import lombok.Data;


import java.time.OffsetDateTime;

@Data
public class ParametersTO {
    private String method;
    private OffsetDateTime responseDate;
    private Integer status;
    private String endpoint;
    private Long requestResponseTime;
    private Double operationTime;
}
