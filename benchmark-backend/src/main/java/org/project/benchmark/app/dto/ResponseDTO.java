package org.project.benchmark.app.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.benchmark.app.entity.MethodType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
@ApiModel(description = "Response object stored in database.")
public class ResponseDTO {

    @ApiModelProperty(notes = "The response's id.")
    private Long id;

    @ApiModelProperty(notes = "The response's endpoint.")
    private String endpoint;

    @ApiModelProperty(notes = "The response's method type.")
    private MethodType methodType;

    @ApiModelProperty(notes = "The response's date.")
    private OffsetDateTime responseDate;

    @Min(1)
    @ApiModelProperty(notes = "Number of users logged in during response.")
    private Integer usersLoggedIn;

    @ApiModelProperty(notes = "The response's request time.")
    private BigDecimal requestResponseTime;

    @ApiModelProperty(notes = "The response's operation time.")
    private BigDecimal operationTime;

    @ApiModelProperty(notes = "Test's object related with this response.")
    @NotNull(message = "This field is required.")
    private TestDTO test;
}
