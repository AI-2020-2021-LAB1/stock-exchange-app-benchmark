package com.project.benchmark.algorithm.service;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndpointParameters {
    private String endpoint;
    private long requestResponseTime;
    private String method;
}
