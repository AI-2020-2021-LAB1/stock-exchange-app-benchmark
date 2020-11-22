package org.project.benchmark.app.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.project.benchmark.app.dto.ConfigurationDTO;
import org.project.benchmark.app.dto.ErrorResponse;
import org.project.benchmark.app.dto.TestDTO;
import org.project.benchmark.app.service.ConfigurationService;
import org.project.benchmark.app.service.TestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/configuration")
@AllArgsConstructor
@Api(value = "Configurations", description = "REST API for configuration's management", tags = "Configurations")
@CrossOrigin("*")
@ApiResponses({
        @ApiResponse(code = 400, message = "The request could not be understood or was missing required parameters.",
                response = ErrorResponse.class),
})
public class ConfigurationController {

    private final ConfigurationService configurationService;
    private final TestService testService;
    private final ObjectMapper mapper;

    @GetMapping
    @ApiOperation(value = "Page configurations.")
    @ApiResponses(@ApiResponse(code = 200, message = " Successfully paged configurations."))
    public Page<ConfigurationDTO> getConfigurations(@ApiIgnore Pageable pageable) {
        return configurationService.getConfigurations(pageable)
                .map(configuration -> mapper.convertValue(configuration,ConfigurationDTO.class));
    }

    @PostMapping
    @ApiOperation(value = "Create new Configuration")
    @ApiResponses({@ApiResponse(code = 200, message = "Configuration's indexes was successfully created."),
            @ApiResponse(code = 400, message = "The request could not be understood or was missing required parameters.",
                    response = ErrorResponse.class),
            @ApiResponse(code = 409, message = "Given configuration already exist.", response = ErrorResponse.class)})
    public void create(@RequestBody @Valid ConfigurationDTO configurationDTO) {
        configurationService.createConfiguration(configurationDTO);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieve configuration by id", response = ConfigurationDTO.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Configuration was successfully retrieved."),
            @ApiResponse(code = 404, message = "Given configuration not found.", response = ErrorResponse.class)})
    public ConfigurationDTO getConfigurationDetails(@ApiParam(value = "Id of desired Configuration", required = true)
                                                        @PathVariable Long id) {
        return mapper.convertValue(configurationService.getConfigurationByID(id),
                ConfigurationDTO.class);
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Update configuration")
    @ApiResponses({@ApiResponse(code = 200, message = "Configuration was successfully updated."),
            @ApiResponse(code = 404, message = "Given configuration not found.", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "The request could not be understood or was missing required parameters.",
                    response = ErrorResponse.class)})
    public void updateConfiguration(
            @ApiParam(value = "Configuration object to update.",
                    required = true) @Valid @RequestBody ConfigurationDTO configurationDTO,
            @ApiParam(value = "Id of desired configuration", required = true) @PathVariable Long id) {
        configurationService.updateConfiguration(configurationDTO, id);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete existing Configuration")
    @ApiResponses({@ApiResponse(code = 200, message = "Configuration's indexes was successfully deleted."),
            @ApiResponse(code = 400, message = "The request could not be understood or was missing required parameters.",
                    response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Given configuration not found.", response = ErrorResponse.class)})
    public void delete(@ApiParam(value = "The id of configuration to delete.", required = true)
                           @PathVariable Long id) {
        configurationService.deleteConfiguration(id);
    }

    @GetMapping("/{id}/test")
    @ApiOperation(value = "Page given configuration's tests")
    @ApiResponses({@ApiResponse(code = 200, message = "Successfully paged configuration's tests."),
            @ApiResponse(code = 400, message = "The request could not be understood or was missing required parameters.",
                    response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Given configuration not found.", response = ErrorResponse.class)
    })
    public Page<TestDTO> getConfigurationTests(@ApiParam(value = "The configuration's id",  required = true)
                                                   @PathVariable Long id, @ApiIgnore Pageable pageable) {
        return testService.getConfigurationTests(id,pageable)
                .map(test -> mapper.convertValue(test,TestDTO.class));
    }
}
