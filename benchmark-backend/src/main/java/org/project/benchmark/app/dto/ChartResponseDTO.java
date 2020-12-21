package org.project.benchmark.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
@Setter
public class ChartResponseDTO {

    private Long testId;

    private List<String> label;

    private List<BigDecimal> operationTimeMin;

    private List<BigDecimal> operationTimeAvg;

    private List<BigDecimal> operationTimeMax;

    private List<BigDecimal> dbQueryTimeMin;

    private List<BigDecimal> dbQueryTimeAvg;

    private List<BigDecimal> dbQueryTimeMax;

    private List<BigDecimal> memoryUsedMin;

    private List<BigDecimal> memoryUsedAvg;

    private List<BigDecimal> memoryUsedMax;

    private List<BigDecimal> memoryUsageMin;

    private List<BigDecimal> memoryUsageAvg;

    private List<BigDecimal> memoryUsageMax;

    private List<BigDecimal> cpuUsageMin;

    private List<BigDecimal> cpuUsageAvg;

    private List<BigDecimal> cpuUsageMax;
}
