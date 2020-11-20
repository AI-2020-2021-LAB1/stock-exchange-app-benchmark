package org.project.benchmark.app.repository;

import org.project.benchmark.app.entity.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long>,
        JpaSpecificationExecutor<Response> {

    @Override
    <S extends Response> S save(S s);

    @Query("SELECT r FROM Response r WHERE r.test.id = :testId ORDER BY r.responseDate desc")
    List<Response> findResponseByTestId(@Param("testId") Long testId);

    @Query("SELECT r FROM Response r WHERE r.test.id = :testId ORDER BY r.responseDate desc")
    Page<Response> findResponseByTestId(@Param("testId") Long testId, Pageable pa);
}
