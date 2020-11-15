package org.project.benchmark.app.service;

import lombok.RequiredArgsConstructor;
import org.project.benchmark.app.entity.Configuration;
import org.project.benchmark.app.repository.ConfigurationRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class ConfigurationServiceImpl implements ConfigurationService {

    private final ConfigurationRepository repository;

    @Override
    public Configuration getConfigurationByID(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Configuration Not Found"));
    }
}
