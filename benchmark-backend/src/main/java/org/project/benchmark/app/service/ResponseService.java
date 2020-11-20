package org.project.benchmark.app.service;

import org.project.benchmark.app.entity.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ResponseService {

    List<Response> getAllResponses();

    Page<Response> getResponses(Pageable pageable);

    Response getResponseByID(Long id);

    Page<Response> getTestResponses(Long testId,Pageable pageable);

    void deleteResponse(Long id);
}
