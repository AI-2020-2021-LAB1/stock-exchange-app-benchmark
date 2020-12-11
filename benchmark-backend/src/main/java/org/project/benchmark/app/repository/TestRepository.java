package org.project.benchmark.app.repository;

import org.project.benchmark.app.entity.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Long>,
        JpaSpecificationExecutor<Test> {

    @Override
    <S extends Test> S save(S s);

    @Query("SELECT t FROM Test t WHERE t.configuration.id = :configurationId ORDER BY t.startDate desc")
    Page<Test> findTestByConfigurationId(@Param("configurationId") Long configurationId, Pageable pa);

    @Query("SELECT t FROM Test t WHERE t.id not in :ids and :date > t.startDate and t.status = 'NEW'")
    List<Test> findTestsToBegin(@Param("ids") Collection<Long> ids, @Param("date") OffsetDateTime date);

    @Query("SELECT t FROM Test t WHERE :date > t.startDate and t.status='NEW'")
    List<Test> findTestsToBegin(@Param("date") OffsetDateTime date);
}
