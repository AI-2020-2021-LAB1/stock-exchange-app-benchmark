package org.project.benchmark.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.project.benchmark.app.dto.ConfigurationDTO;
import org.project.benchmark.app.entity.Configuration;
import org.project.benchmark.app.entity.Test;
import org.project.benchmark.app.repository.ConfigurationRepository;
import org.project.benchmark.app.repository.ResponseRepository;
import org.project.benchmark.app.repository.TestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfigurationServiceImpl implements ConfigurationService {

    private final ConfigurationRepository repository;
    private final TestRepository testRepository;
    private final ResponseRepository responseRepository;
    private final ObjectMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Page<Configuration> getConfigurations(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<Configuration> getAllConfigurations() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Configuration getConfigurationByID(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Configuration Not Found"));
    }

    @Override
    public Configuration updateConfiguration(Configuration configuration) {
        return  repository.save(configuration);
    }

    @Override
    @Transactional
    public void updateConfiguration(ConfigurationDTO configurationDTO, Long id) {
        Configuration configuration = getConfigurationByID(id);
        Optional<Configuration> existingConfiguration = repository
                .findByName(configurationDTO.getName().trim());
        if (existingConfiguration.isPresent() &&
                !configuration.getId().equals(existingConfiguration.get().getId())) {
            throw new EntityExistsException("Configuration with given name already exists.");
        }
        configurationDTOToConfiguration(configurationDTO, configuration);
        repository.save(configuration);
    }

    @Override
    @Transactional
    public void createConfiguration(ConfigurationDTO configurationDTO) {
        Configuration configuration = validateConfigurationDTO(configurationDTO);
        repository.save(configuration);
    }

    @Override
    @Transactional
    public void deleteConfiguration(Long id) {
        Configuration configuration = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Configuration not found"));
        for (Test test : configuration.getTests()) {
            test.getResponses().forEach(responseRepository::delete);
            testRepository.delete(test);
        }
        repository.delete(configuration);
    }


    private Configuration validateConfigurationDTO(ConfigurationDTO configurationDTO) {
        Optional<Configuration> existingConfiguration = repository
                .findByName(configurationDTO.getName().trim());
        if (existingConfiguration.isPresent()) {
            throw new EntityExistsException("Configuration with given name already exists.");
        }
        Configuration configuration = mapper.convertValue(configurationDTO, Configuration.class);
        configurationDTOToConfiguration(configurationDTO, configuration);
        return configuration;
    }

    private void configurationDTOToConfiguration(ConfigurationDTO configurationDTO, Configuration configuration) {
        configuration.setName(configurationDTO.getName().trim());
        configuration.setCertaintyLevel(configurationDTO.getCertaintyLevel());
        configuration.setLoginAllStocks(configurationDTO.getLoginAllStocks());
        configuration.setLoginMakeOrder(configurationDTO.getLoginMakeOrder());
        configuration.setLoginOwnedStocks(configurationDTO.getLoginOwnedStocks());
        configuration.setLoginUserOrders(configurationDTO.getLoginUserOrders());
        configuration.setAllStocksEnd(configurationDTO.getAllStocksEnd());
        configuration.setAllStocksMakeOrder(configurationDTO.getAllStocksMakeOrder());
        configuration.setOwnedStocksEnd(configurationDTO.getOwnedStocksEnd());
        configuration.setOwnedStocksMakeOrder(configurationDTO.getOwnedStocksMakeOrder());
        configuration.setUserOrderDeleteOrder(configurationDTO.getUserOrderDeleteOrder());
        configuration.setUserOrdersEnd(configurationDTO.getUserOrdersEnd());
        configuration.setUserOrdersMakeOrder(configurationDTO.getUserOrdersMakeOrder());
        configuration.setMakeOrderBuyOrder(configurationDTO.getMakeOrderBuyOrder());
        configuration.setMakeOrderSellOrder(configurationDTO.getMakeOrderSellOrder());
        configuration.setNoOfMoney(configurationDTO.getNoOfMoney());
        configuration.setNoOfOperations(configurationDTO.getNoOfOperations());
    }
}
