package org.project.benchmark.app.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.project.benchmark.app.dto.ErrorResponse;
import org.project.benchmark.app.dto.ResponseDTO;
import org.project.benchmark.app.service.ResponseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/response")
@AllArgsConstructor
@Api(value = "Responses", description = "REST API for response's management", tags = "Responses")
@CrossOrigin("*")
@ApiResponses({
        @ApiResponse(code = 400, message = "The request could not be understood or was missing required parameters.",
                response = ErrorResponse.class),
})
public class ResponseController {

    private final ResponseService responseService;
    private final ObjectMapper mapper;

    @GetMapping
    @ApiOperation(value = "Page response.")
    @ApiResponses(@ApiResponse(code = 200, message = " Successfully paged responses."))
    public Page<ResponseDTO> getResponses(@ApiIgnore Pageable pageable) {
        return responseService.getResponses(pageable)
                .map(response -> mapper.convertValue(response,ResponseDTO.class));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieve response by id", response = ResponseDTO.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Response was successfully retrieved."),
            @ApiResponse(code = 404, message = "Given response not found.", response = ErrorResponse.class)})
    public ResponseDTO getResponseDetails(@ApiParam(value = "Id of desired Response", required = true)
                                              @PathVariable Long id) {
        return mapper.convertValue(responseService.getResponseByID(id),
                ResponseDTO.class);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete existing response")
    @ApiResponses({@ApiResponse(code = 200, message = "Response's indexes was successfully deleted."),
            @ApiResponse(code = 400, message = "The request could not be understood or was missing required parameters.",
                    response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Given response not found.", response = ErrorResponse.class)})
    public void delete(@ApiParam(value = "The id of response to delete.", required = true) @PathVariable Long id) {
        responseService.deleteResponse(id);
    }
}
