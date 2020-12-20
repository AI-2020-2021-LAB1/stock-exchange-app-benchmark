package org.project.benchmark.app.service;

import lombok.RequiredArgsConstructor;
import org.project.benchmark.app.dto.ChartResponseDTO;
import org.project.benchmark.app.entity.MethodType;
import org.project.benchmark.app.repository.ResponseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChartResponseServiceImpl implements ChartResponseService {

    private final ResponseRepository responseRepository;

    @Override
    @Transactional(readOnly = true)
    public ChartResponseDTO getChartResponseForAllEndpoints(Long testId){
        List<String> endpoints = getEndpoints(testId);
        List<BigDecimal> minDBQueryTime = new ArrayList<>();
        List<BigDecimal> avgDBQueryTime = new ArrayList<>();
        List<BigDecimal> maxDBQueryTime = new ArrayList<>();
        List<BigDecimal> minOperationTime = new ArrayList<>();
        List<BigDecimal> avgOperationTime = new ArrayList<>();
        List<BigDecimal> maxOperationTime = new ArrayList<>();
        List<BigDecimal> minMemoryUsed = new ArrayList<>();
        List<BigDecimal> avgMemoryUsed = new ArrayList<>();
        List<BigDecimal> maxMemoryUsed = new ArrayList<>();
        List<BigDecimal> minMemoryUsage = new ArrayList<>();
        List<BigDecimal> avgMemoryUsage = new ArrayList<>();
        List<BigDecimal> maxMemoryUsage = new ArrayList<>();
        List<BigDecimal> minCPUUsage = new ArrayList<>();
        List<BigDecimal> avgCPUUsage = new ArrayList<>();
        List<BigDecimal> maxCPUUsage = new ArrayList<>();
        for(String endpoint:endpoints){
            minDBQueryTime.add(responseRepository.findMinDBQueryTimeForEndpoint(endpoint,testId).setScale(2, RoundingMode.HALF_EVEN));
            avgDBQueryTime.add(responseRepository.findAvgDBQueryTimeForEndpoint(endpoint,testId).setScale(2,RoundingMode.HALF_EVEN));
            maxDBQueryTime.add(responseRepository.findMaxDBQueryTimeForEndpoint(endpoint,testId).setScale(2,RoundingMode.HALF_EVEN));
            minOperationTime.add(responseRepository.findMinOperationTimeForEndpoint(endpoint,testId).setScale(2,RoundingMode.HALF_EVEN));
            avgOperationTime.add(responseRepository.findAvgOperationTimeForEndpoint(endpoint,testId).setScale(2,RoundingMode.HALF_EVEN));
            maxOperationTime.add(responseRepository.findMaxOperationTimeForEndpoint(endpoint,testId).setScale(2,RoundingMode.HALF_EVEN));
            minMemoryUsed.add(responseRepository.findMinMemoryUsedForEndpoint(endpoint,testId).setScale(2,RoundingMode.HALF_EVEN));
            avgMemoryUsed.add(responseRepository.findAvgMemoryUsedForEndpoint(endpoint,testId).setScale(2,RoundingMode.HALF_EVEN));
            maxMemoryUsed.add(responseRepository.findMaxMemoryUsedForEndpoint(endpoint,testId).setScale(2,RoundingMode.HALF_EVEN));
            minMemoryUsage.add(responseRepository.findMinMemoryUsageForEndpoint(endpoint,testId).setScale(2,RoundingMode.HALF_EVEN));
            avgMemoryUsage.add(responseRepository.findAvgMemoryUsageForEndpoint(endpoint,testId).setScale(2,RoundingMode.HALF_EVEN));
            maxMemoryUsage.add(responseRepository.findMaxMemoryUsageForEndpoint(endpoint,testId).setScale(2,RoundingMode.HALF_EVEN));
            minCPUUsage.add(responseRepository.findMinCPUUsageForEndpoint(endpoint,testId).setScale(2,RoundingMode.HALF_EVEN));
            avgCPUUsage.add(responseRepository.findAvgCPUUsageForEndpoint(endpoint,testId).setScale(2,RoundingMode.HALF_EVEN));
            maxCPUUsage.add(responseRepository.findMaxCPUUsageForEndpoint(endpoint,testId).setScale(2,RoundingMode.HALF_EVEN));
        }
        return new ChartResponseDTO(testId,endpoints,
                minOperationTime,avgOperationTime,maxOperationTime,
                minDBQueryTime,avgDBQueryTime,maxDBQueryTime,
                minMemoryUsed,avgMemoryUsed,maxMemoryUsed,
                minMemoryUsage,avgMemoryUsage,maxMemoryUsage,
                minCPUUsage,avgCPUUsage,maxCPUUsage);
    }

    @Override
    @Transactional(readOnly = true)
    public ChartResponseDTO getChartResponseForAllMethod(Long testId) {
        List<MethodType> methodList = getMethods(testId);
        List<BigDecimal> minDBQueryTime = new ArrayList<>();
        List<BigDecimal> avgDBQueryTime = new ArrayList<>();
        List<BigDecimal> maxDBQueryTime = new ArrayList<>();
        List<BigDecimal> minOperationTime = new ArrayList<>();
        List<BigDecimal> avgOperationTime = new ArrayList<>();
        List<BigDecimal> maxOperationTime = new ArrayList<>();
        List<BigDecimal> minMemoryUsed = new ArrayList<>();
        List<BigDecimal> avgMemoryUsed = new ArrayList<>();
        List<BigDecimal> maxMemoryUsed = new ArrayList<>();
        List<BigDecimal> minMemoryUsage = new ArrayList<>();
        List<BigDecimal> avgMemoryUsage = new ArrayList<>();
        List<BigDecimal> maxMemoryUsage = new ArrayList<>();
        List<BigDecimal> minCPUUsage = new ArrayList<>();
        List<BigDecimal> avgCPUUsage = new ArrayList<>();
        List<BigDecimal> maxCPUUsage = new ArrayList<>();
        List<String> methods = methodList.stream().map(Enum::name).collect(Collectors.toList());
        for(MethodType method:methodList){
            minDBQueryTime.add(responseRepository.findMinDBQueryTimeForMethod(method,testId).setScale(2,RoundingMode.HALF_EVEN));
            avgDBQueryTime.add(responseRepository.findAvgDBQueryTimeForMethod(method,testId).setScale(2,RoundingMode.HALF_EVEN));
            maxDBQueryTime.add(responseRepository.findMaxDBQueryTimeForMethod(method,testId).setScale(2,RoundingMode.HALF_EVEN));
            minOperationTime.add(responseRepository.findMinOperationTimeForMethod(method,testId).setScale(2,RoundingMode.HALF_EVEN));
            avgOperationTime.add(responseRepository.findAvgOperationTimeForMethod(method,testId).setScale(2,RoundingMode.HALF_EVEN));
            maxOperationTime.add(responseRepository.findMaxOperationTimeForMethod(method,testId).setScale(2,RoundingMode.HALF_EVEN));
            minMemoryUsed.add(responseRepository.findMinMemoryUsedForMethod(method,testId).setScale(2,RoundingMode.HALF_EVEN));
            avgMemoryUsed.add(responseRepository.findAvgMemoryUsedForMethod(method,testId).setScale(2,RoundingMode.HALF_EVEN));
            maxMemoryUsed.add(responseRepository.findMaxMemoryUsedForMethod(method,testId).setScale(2,RoundingMode.HALF_EVEN));
            minMemoryUsage.add(responseRepository.findMinMemoryUsageForMethod(method,testId).setScale(2,RoundingMode.HALF_EVEN));
            avgMemoryUsage.add(responseRepository.findAvgMemoryUsageForMethod(method,testId).setScale(2,RoundingMode.HALF_EVEN));
            maxMemoryUsage.add(responseRepository.findMaxMemoryUsageForMethod(method,testId).setScale(2,RoundingMode.HALF_EVEN));
            minCPUUsage.add(responseRepository.findMinCPUUsageForMethod(method,testId).setScale(2,RoundingMode.HALF_EVEN));
            avgCPUUsage.add(responseRepository.findAvgCPUUsageForMethod(method,testId).setScale(2,RoundingMode.HALF_EVEN));
            maxCPUUsage.add(responseRepository.findMaxCPUUsageForMethod(method,testId).setScale(2,RoundingMode.HALF_EVEN));
        }
        return new ChartResponseDTO(testId,methods,
                minOperationTime,avgOperationTime,maxOperationTime,
                minDBQueryTime,avgDBQueryTime,maxDBQueryTime,
                minMemoryUsed,avgMemoryUsed,maxMemoryUsed,
                minMemoryUsage,avgMemoryUsage,maxMemoryUsage,
                minCPUUsage,avgCPUUsage,maxCPUUsage);
    }

    private List<String> getEndpoints(Long test_id) {
        return responseRepository.findEndpointsList(test_id);
    }

    private List<MethodType> getMethods(Long test_id) {
        return responseRepository.findMethodTypeList(test_id);
    }
}
