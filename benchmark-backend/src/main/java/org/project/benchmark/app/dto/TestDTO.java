package org.project.benchmark.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.benchmark.app.entity.TestStatus;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

@JsonIgnoreProperties(value = { "reponses" })
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

    @ApiModelProperty(notes = "Configuration's object related with this test.")
    @NotNull(message = "This field is required.")
    private ConfigurationDTO configuration;

    @Min(value=1, message="must be equal or greater than 1")
    @ApiModelProperty(notes = "User count specified for test")
    @NotNull(message = "This field is required.")
    private Integer userCount;

    @Min(value=1, message="must be equal or greater than 1")
    @ApiModelProperty(notes = "Stock count specified for test")
    @NotNull(message = "This field is required.")
    private Integer stockCount;

    @Min(value=1, message="must be equal or greater than 1")
    @ApiModelProperty(notes = "iterations specified for test")
    @NotNull(message = "This field is required.")
    private Integer iterations;

    @ApiModelProperty(notes = "current status of test (only for GET)")
    private TestStatus status;
}
