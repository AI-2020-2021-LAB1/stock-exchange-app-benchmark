package com.project.benchmark.algorithm.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EndpointParameters {
    private String endpoint;
    private long requestResponseTime;
    private String method;
}
