package org.project.benchmark.app.service;

import org.project.benchmark.app.dto.ConfigurationDTO;
import org.project.benchmark.app.entity.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ConfigurationService {

    List<Configuration> getAllConfigurations();

    Page<Configuration> getConfigurations(Pageable pageable);

    Configuration getConfigurationByID(Long id);

    Configuration updateConfiguration(Configuration configuration);

    void updateConfiguration(ConfigurationDTO configurationDTO, Long id);

    void createConfiguration(ConfigurationDTO configurationDTO);

    void deleteConfiguration(Long id);
}
