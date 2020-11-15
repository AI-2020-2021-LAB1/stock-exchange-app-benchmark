package org.project.benchmark.app.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.benchmark.app.dto.ConfigurationDTO;
import org.project.benchmark.app.dto.ErrorResponse;
import org.project.benchmark.app.service.ConfigurationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/configuration")
@AllArgsConstructor
@Api(value = "Configurations", description = "REST API for configuration's management", tags = "Configurations")
@CrossOrigin("*")// ?
@ApiResponses({
        @ApiResponse(code = 400, message = "The request could not be understood or was missing required parameters.",
                response = ErrorResponse.class),
})
public class ConfigurationController {

    private final ConfigurationService configurationService;
    private final ModelMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<ConfigurationDTO> getConfigurationDetails(@PathVariable Long id) {
        ConfigurationDTO configurationDTO = mapper.map(configurationService.getConfigurationByID(id),
                ConfigurationDTO.class);
        return new ResponseEntity<>(configurationDTO, HttpStatus.OK);
    }
}
