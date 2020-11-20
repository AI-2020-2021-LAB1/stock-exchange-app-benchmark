package org.project.benchmark.app.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
@ApiModel(description = "Test object stored in database.")
public class TestDTO {

    @ApiModelProperty(notes = "The test's id.")
    private Long id;

    @ApiModelProperty(notes = "The test's start date.")
    @FutureOrPresent
    private OffsetDateTime startDate;

    @ApiModelProperty(notes = "The test's end date.")
    @Future
    private OffsetDateTime endDate;

    @ApiModelProperty(notes = "Configuration's object related with this test.")
    @NotNull(message = "This field is required.")
    private ConfigurationDTO configuration;
}
