package org.project.benchmark.app.service;

import lombok.RequiredArgsConstructor;
import org.project.benchmark.app.entity.Response;
import org.project.benchmark.app.repository.ResponseRepository;
import org.project.benchmark.app.repository.TestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResponseServiceImpl implements ResponseService {

    private final ResponseRepository responseRepository;
    private final TestRepository testRepository;

    @Override
    public List<Response> getAllResponses() {
        return responseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Response> getResponses(Pageable pageable, Specification<Response> specification) {
        return responseRepository.findAll(specification, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Response getResponseByID(Long id) {
        return responseRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Response Not Found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Response> getTestResponses(Long testId,Pageable pageable) {
        testRepository.findById(testId).orElseThrow(() ->
                new EntityNotFoundException("Test Not Found"));
        return responseRepository.findResponseByTestId(testId,pageable);
    }

    @Override
    @Transactional
    public void deleteResponse(Long id) {
        Response response = responseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Response not found"));
        responseRepository.delete(response);
    }
}
