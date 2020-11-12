package org.project.benchmark.app.rest;

import org.project.benchmark.app.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionMapper extends DefaultHandlerExceptionResolver {
    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEntityNotFoundException (EntityNotFoundException exc) {
        return ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(exc.getMessage())
                .build();
    }

    @ExceptionHandler({EntityExistsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflict (RuntimeException exc) {
        return ErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .message(exc.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, Collection<String>> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().stream()
                .map(error -> (FieldError) error)
                .forEach(error -> {
                    errors.putIfAbsent(error.getField(), new ArrayList<>());
                    errors.get(error.getField()).add(error.getDefaultMessage());
                });
        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Validation error")
                .errors(errors)
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse httpMessageNotReadableException() {
        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Malformed JSON request")
                .build();
    }
}
