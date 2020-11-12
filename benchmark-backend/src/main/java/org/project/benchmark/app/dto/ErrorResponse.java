package org.project.benchmark.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private int status;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object errors;
}
