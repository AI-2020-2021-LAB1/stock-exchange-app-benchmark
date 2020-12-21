package org.project.benchmark.app.repository;

import org.project.benchmark.app.entity.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Long>,
        JpaSpecificationExecutor<Configuration> {

    @Override
    <S extends Configuration> S save(S s);

    Optional<Configuration> findByName(String name);
}
