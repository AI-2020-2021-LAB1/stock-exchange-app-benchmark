package org.project.benchmark.app.repository;

import org.project.benchmark.app.entity.MethodType;
import org.project.benchmark.app.entity.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long>,
        JpaSpecificationExecutor<Response> {

    @Override
    <S extends Response> S save(S s);

    @Override
    Page<Response> findAll(@Nullable Specification<Response> specification, Pageable pageable);

    @Query("SELECT r FROM Response r WHERE r.test.id = :testId ORDER BY r.responseDate desc")
    List<Response> findResponseByTestId(@Param("testId") Long testId);

    @Query("SELECT r FROM Response r WHERE r.test.id = :testId ORDER BY r.responseDate desc")
    Page<Response> findResponseByTestId(@Param("testId") Long testId, Pageable pageable);

    @Query("Select MIN(r.operationTime) FROM Response r WHERE r.methodType =:methodType and r.test.id = :test")
    BigDecimal findMinOperationTimeForMethod(@Param("methodType")MethodType methodType, @Param("test") Long testId );

    @Query("Select AVG(r.operationTime) FROM Response r WHERE r.methodType =:methodType and r.test.id = :test")
    BigDecimal findAvgOperationTimeForMethod(@Param("methodType")MethodType methodType, @Param("test") Long testId );

    @Query("Select MAX(r.operationTime) FROM Response r WHERE r.methodType =:methodType and r.test.id = :test")
    BigDecimal findMaxOperationTimeForMethod(@Param("methodType")MethodType methodType, @Param("test") Long testId );

    @Query("Select MIN(r.dbQueryTime) FROM Response r WHERE r.methodType = :methodType and r.test.id = :test")
    BigDecimal findMinDBQueryTimeForMethod(@Param("methodType")MethodType methodType, @Param("test") Long testId );

    @Query("Select AVG(r.dbQueryTime) FROM Response r WHERE r.methodType =:methodType and r.test.id = :test")
    BigDecimal findAvgDBQueryTimeForMethod(@Param("methodType")MethodType methodType, @Param("test") Long testId );

    @Query("Select MAX(r.dbQueryTime) FROM Response r WHERE r.methodType =:methodType and r.test.id = :test")
    BigDecimal findMaxDBQueryTimeForMethod(@Param("methodType")MethodType methodType, @Param("test") Long testId );

    @Query("Select MIN(r.memoryUsed) FROM Response r WHERE r.methodType =:methodType and r.test.id = :test")
    BigDecimal findMinMemoryUsedForMethod(@Param("methodType")MethodType methodType, @Param("test") Long testId );

    @Query("Select AVG(r.memoryUsed) FROM Response r WHERE r.methodType =:methodType and r.test.id = :test")
    BigDecimal findAvgMemoryUsedForMethod(@Param("methodType")MethodType methodType, @Param("test") Long testId );

    @Query("Select MAX(r.memoryUsed) FROM Response r WHERE r.methodType =:methodType and r.test.id = :test")
    BigDecimal findMaxMemoryUsedForMethod(@Param("methodType")MethodType methodType, @Param("test") Long testId );

    @Query("Select MIN(r.memoryUsage) FROM Response r WHERE r.methodType =:methodType and r.test.id = :test")
    BigDecimal findMinMemoryUsageForMethod(@Param("methodType")MethodType methodType, @Param("test") Long testId );

    @Query("Select AVG(r.memoryUsage) FROM Response r WHERE r.methodType =:methodType and r.test.id = :test")
    BigDecimal findAvgMemoryUsageForMethod(@Param("methodType")MethodType methodType, @Param("test") Long testId );

    @Query("Select MAX(r.memoryUsage) FROM Response r WHERE r.methodType =:methodType and r.test.id = :test")
    BigDecimal findMaxMemoryUsageForMethod(@Param("methodType")MethodType methodType, @Param("test") Long testId );

    @Query("Select MIN(r.cpuUsage) FROM Response r WHERE r.methodType =:methodType and r.test.id = :test")
    BigDecimal findMinCPUUsageForMethod(@Param("methodType")MethodType methodType, @Param("test") Long testId );

    @Query("Select AVG(r.cpuUsage) FROM Response r WHERE r.methodType =:methodType and r.test.id = :test")
    BigDecimal findAvgCPUUsageForMethod(@Param("methodType")MethodType methodType, @Param("test") Long testId );

    @Query("Select MAX(r.cpuUsage) FROM Response r WHERE r.methodType =:methodType and r.test.id = :test")
    BigDecimal findMaxCPUUsageForMethod(@Param("methodType")MethodType methodType, @Param("test") Long testId );

    @Query("Select MIN(r.operationTime) FROM Response r WHERE r.endpoint =:endpoint and r.test.id = :test")
    BigDecimal findMinOperationTimeForEndpoint(@Param("endpoint")String endpoint, @Param("test") Long testId );

    @Query("Select AVG(r.operationTime) FROM Response r WHERE r.endpoint =:endpoint and r.test.id = :test")
    BigDecimal findAvgOperationTimeForEndpoint(@Param("endpoint")String endpoint, @Param("test") Long testId );

    @Query("Select MAX(r.operationTime) FROM Response r WHERE r.endpoint =:endpoint and r.test.id = :test")
    BigDecimal findMaxOperationTimeForEndpoint(@Param("endpoint")String endpoint, @Param("test") Long testId );

    @Query("Select MIN(r.dbQueryTime) FROM Response r WHERE r.endpoint = :endpoint and r.test.id = :test")
    BigDecimal findMinDBQueryTimeForEndpoint(@Param("endpoint")String endpoint, @Param("test") Long testId );

    @Query("Select AVG(r.dbQueryTime) FROM Response r WHERE r.endpoint =:endpoint and r.test.id = :test")
    BigDecimal findAvgDBQueryTimeForEndpoint(@Param("endpoint")String endpoint, @Param("test") Long testId );

    @Query("Select MAX(r.dbQueryTime) FROM Response r WHERE r.endpoint =:endpoint and r.test.id = :test")
    BigDecimal findMaxDBQueryTimeForEndpoint(@Param("endpoint")String endpoint, @Param("test") Long testId );

    @Query("Select MIN(r.memoryUsed) FROM Response r WHERE r.endpoint = :endpoint and r.test.id = :test")
    BigDecimal findMinMemoryUsedForEndpoint(@Param("endpoint")String endpoint, @Param("test") Long testId );

    @Query("Select AVG(r.memoryUsed) FROM Response r WHERE r.endpoint =:endpoint and r.test.id = :test")
    BigDecimal findAvgMemoryUsedForEndpoint(@Param("endpoint")String endpoint, @Param("test") Long testId );

    @Query("Select MAX(r.memoryUsed) FROM Response r WHERE r.endpoint =:endpoint and r.test.id = :test")
    BigDecimal findMaxMemoryUsedForEndpoint(@Param("endpoint")String endpoint, @Param("test") Long testId );

    @Query("Select MIN(r.memoryUsage) FROM Response r WHERE r.endpoint = :endpoint and r.test.id = :test")
    BigDecimal findMinMemoryUsageForEndpoint(@Param("endpoint")String endpoint, @Param("test") Long testId );

    @Query("Select AVG(r.memoryUsage) FROM Response r WHERE r.endpoint =:endpoint and r.test.id = :test")
    BigDecimal findAvgMemoryUsageForEndpoint(@Param("endpoint")String endpoint, @Param("test") Long testId );

    @Query("Select MAX(r.memoryUsage) FROM Response r WHERE r.endpoint =:endpoint and r.test.id = :test")
    BigDecimal findMaxMemoryUsageForEndpoint(@Param("endpoint")String endpoint, @Param("test") Long testId );

    @Query("Select MIN(r.cpuUsage) FROM Response r WHERE r.endpoint = :endpoint and r.test.id = :test")
    BigDecimal findMinCPUUsageForEndpoint(@Param("endpoint")String endpoint, @Param("test") Long testId );

    @Query("Select AVG(r.cpuUsage) FROM Response r WHERE r.endpoint =:endpoint and r.test.id = :test")
    BigDecimal findAvgCPUUsageForEndpoint(@Param("endpoint")String endpoint, @Param("test") Long testId );

    @Query("Select MAX(r.cpuUsage) FROM Response r WHERE r.endpoint =:endpoint and r.test.id = :test")
    BigDecimal findMaxCPUUsageForEndpoint(@Param("endpoint")String endpoint, @Param("test") Long testId );

    @Query("Select DISTINCT r.endpoint FROM Response r WHERE r.test.id = :test")
    List<String> findEndpointsList(@Param("test") Long testId);

    @Query("Select DISTINCT r.methodType FROM Response r WHERE r.test.id = :test")
    List<MethodType> findMethodTypeList(@Param("test") Long testId);
}
