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

@Service
@RequiredArgsConstructor
public class ChartResponseServiceImpl implements ChartResponseService {

    private final ResponseRepository responseRepository;

    List<BigDecimal> minDBQueryTime = new ArrayList<>();
    List<BigDecimal> avgDBQueryTime = new ArrayList<>();
    List<BigDecimal> maxDBQueryTime = new ArrayList<>();
    List<BigDecimal> minOperationTime = new ArrayList<>();
    List<BigDecimal> avgOperationTime = new ArrayList<>();
    List<BigDecimal> maxOperationTime = new ArrayList<>();

    @Override
    @Transactional(readOnly = true)
    public ChartResponseDTO getChartResponseForAllEndpoints(){
        List<String> endpoints = getEndpoints();
        clearTimes();
        for(String endpoint:endpoints){
            minDBQueryTime.add(responseRepository.findMinDBQueryTimeForEndpoint(endpoint).setScale(2, RoundingMode.HALF_EVEN));
            avgDBQueryTime.add(responseRepository.findAvgDBQueryTimeForEndpoint(endpoint).setScale(2,RoundingMode.HALF_EVEN));
            maxDBQueryTime.add(responseRepository.findMaxDBQueryTimeForEndpoint(endpoint).setScale(2,RoundingMode.HALF_EVEN));
            minOperationTime.add(responseRepository.findMinOperationTimeForEndpoint(endpoint).setScale(2,RoundingMode.HALF_EVEN));
            avgOperationTime.add(responseRepository.findAvgOperationTimeForEndpoint(endpoint).setScale(2,RoundingMode.HALF_EVEN));
            maxOperationTime.add(responseRepository.findMaxOperationTimeForEndpoint(endpoint).setScale(2,RoundingMode.HALF_EVEN));
        }
        return new ChartResponseDTO(endpoints,
                minOperationTime,avgOperationTime,maxOperationTime,
                minDBQueryTime,avgDBQueryTime,maxDBQueryTime);
    }

    @Override
    @Transactional(readOnly = true)
    public ChartResponseDTO getChartResponseForAllMethod() {
        List<MethodType> methodList = getMethods();
        clearTimes();
        List<String> methods = Collections.singletonList(methodList.toString());
        for(MethodType method:methodList){
            minDBQueryTime.add(responseRepository.findMinDBQueryTimeForMethod(method).setScale(2,RoundingMode.HALF_EVEN));
            avgDBQueryTime.add(responseRepository.findAvgDBQueryTimeForMethod(method).setScale(2,RoundingMode.HALF_EVEN));
            maxDBQueryTime.add(responseRepository.findMaxDBQueryTimeForMethod(method).setScale(2,RoundingMode.HALF_EVEN));
            minOperationTime.add(responseRepository.findMinOperationTimeForMethod(method).setScale(2,RoundingMode.HALF_EVEN));
            avgOperationTime.add(responseRepository.findAvgOperationTimeForMethod(method).setScale(2,RoundingMode.HALF_EVEN));
            maxOperationTime.add(responseRepository.findMaxOperationTimeForMethod(method).setScale(2,RoundingMode.HALF_EVEN));
        }
        return new ChartResponseDTO(methods,
                minOperationTime,avgOperationTime,maxOperationTime,
                minDBQueryTime,avgDBQueryTime,maxDBQueryTime);
    }

    private void clearTimes() {
        minDBQueryTime.clear();
        avgDBQueryTime.clear();
        maxDBQueryTime.clear();
        minOperationTime.clear();
        avgOperationTime.clear();
        maxOperationTime.clear();
    }

    private List<String> getEndpoints() {
        return responseRepository.findEndpointsList();
    }

    private List<MethodType> getMethods() {
        return responseRepository.findMethodTypeList();
    }
}
