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

    private List<String> label;

    private List<BigDecimal> operationTimeMin;

    private List<BigDecimal> operationTimeAvg;

    private List<BigDecimal> operationTimeMax;

    private List<BigDecimal> dbQueryTimeMin;

    private List<BigDecimal> dbQueryTimeAvg;

    private List<BigDecimal> dbQueryTimeMax;

}
