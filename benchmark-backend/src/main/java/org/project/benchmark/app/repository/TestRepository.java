package org.project.benchmark.app.repository;

import org.project.benchmark.app.entity.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Long>,
        JpaSpecificationExecutor<Test> {

    @Override
    <S extends Test> S save(S s);

    @Query("SELECT t FROM Test t WHERE t.configuration.id = :configurationId ORDER BY t.startDate desc")
    List<Test> findTestByConfigurationId(@Param("configurationId") Long configurationId);

    @Query("SELECT t FROM Test t WHERE t.configuration.id = :configurationId ORDER BY t.startDate desc")
    Page<Test> findTestByConfigurationId(@Param("configurationId") Long configurationId, Pageable pa);
}
