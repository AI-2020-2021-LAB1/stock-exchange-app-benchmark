package com.project.benchmark.algorithm.internal;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseTO {
    private String endpoint;
    private Integer statusCode;
    private String methodType;
    @Builder.Default
    private OffsetDateTime responseDate = OffsetDateTime.now();
    private Integer usersLoggedIn;
    private BigDecimal requestResponseTime;
    private BigDecimal operationTime;
    private BigDecimal dbQueryTime;
}
