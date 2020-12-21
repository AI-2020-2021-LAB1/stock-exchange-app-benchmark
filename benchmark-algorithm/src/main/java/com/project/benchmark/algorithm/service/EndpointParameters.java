package com.project.benchmark.algorithm.service;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EndpointParameters {
    private String endpoint;
    private long requestResponseTime;
    private String method;
}
