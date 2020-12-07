package org.project.benchmark.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.project.benchmark.app.dto.TestDTO;
import org.project.benchmark.app.entity.Configuration;
import org.project.benchmark.app.entity.Test;
import org.project.benchmark.app.repository.ConfigurationRepository;
import org.project.benchmark.app.repository.TestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestRepository repository;
    private final ConfigurationRepository configurationRepository;
    private final ObjectMapper mapper;

    @Override
    public List<Test> getAllTests()  {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Test> getTests(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Test getTestByID(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Test Not Found"));
    }

    @Override
    public Test updateTest(Test test) {
        return repository.save(test);
    }

    @Override
    @Transactional
    public void updateTest(TestDTO testDTO, Long id) {
        Test test = getTestByID(id);
        test.setEndDate(testDTO.getEndDate());
        test.setStartDate(testDTO.getStartDate());
        repository.save(test);
    }

    @Override
    @Transactional
    public void createTest(TestDTO testDTO) {
        Test test = mapper.convertValue(testDTO,Test.class);
        testDTOToTest(testDTO, test);
        repository.save(test);
    }

    @Override
    @Transactional
    public void deleteTest(Long id) {
        Test test = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Test not found"));
        repository.delete(test);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Test> getConfigurationTests(Long configurationId, Pageable pageable) {
        configurationRepository.findById(configurationId).orElseThrow(() ->
                new EntityNotFoundException("Configuration Not Found"));
        return repository.findTestByConfigurationId(configurationId,pageable);
    }

    private void testDTOToTest(TestDTO testDTO, Test test){
        test.setConfiguration(mapper.convertValue(testDTO.getConfiguration(), Configuration.class));
        test.setEndDate(testDTO.getEndDate());
        test.setStartDate(testDTO.getStartDate());
    }
}
