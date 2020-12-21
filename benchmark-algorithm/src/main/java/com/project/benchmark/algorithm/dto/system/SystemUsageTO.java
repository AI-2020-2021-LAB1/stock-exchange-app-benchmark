package com.project.benchmark.algorithm.dto.system;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class SystemUsageTO {
    private Double cpuUsage;
    private Integer id;
    private Double memoryUsage;
    private Double memoryUsed;
    private OffsetDateTime timestamp;
}
