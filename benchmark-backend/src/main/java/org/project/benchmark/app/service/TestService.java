package org.project.benchmark.app.service;

import org.project.benchmark.app.dto.TestDTO;
import org.project.benchmark.app.dto.TestProgressDTO;
import org.project.benchmark.app.entity.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TestService {

    List<Test> getAllTests();

    Page<Test> getTests(Pageable pageable);

    Test getTestByID(Long id);

    List<TestProgressDTO> getRunningTests();

    Test updateTest(Test test);

    void updateTest(TestDTO testDTO, Long id);

    void createTest(TestDTO testDTO);

    void deleteTest(Long id);

    Page<Test> getConfigurationTests(Long configurationId, Pageable pageable);
}
