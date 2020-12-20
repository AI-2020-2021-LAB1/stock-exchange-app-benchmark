package org.project.benchmark.app.service;

import org.project.benchmark.app.dto.ChartResponseDTO;

public interface ChartResponseService {

    ChartResponseDTO getChartResponseForAllEndpoints(Long testId);

    ChartResponseDTO getChartResponseForAllMethod(Long testId);

}
