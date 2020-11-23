package org.project.benchmark.app.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.project.benchmark.app.dto.ErrorResponse;
import org.project.benchmark.app.dto.ResponseDTO;
import org.project.benchmark.app.repository.specification.ResponseSpecification;
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
    @ApiOperation(value = "Page and filter response.")
    @ApiResponses(@ApiResponse(code = 200, message = " Successfully paged and filter responses."))
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N).", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.", defaultValue = "20"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. Multiple sort criteria are supported."),
            @ApiImplicitParam(name = "testId", dataType = "integer", paramType = "query",
                    value = "Filtering criteria for field `testId` (omitted if null)"),
            @ApiImplicitParam(name = "endpoint", dataType = "string", paramType = "query",
                    value = "Filtering criteria for field `endpoint`. (omitted if null)"),
            @ApiImplicitParam(name = "statusCode", dataType = "integer", paramType = "query",
                    value = "Filtering criteria for field `statusCode`. (omitted if null)"),
            @ApiImplicitParam(name = "methodType", dataType = "string", paramType = "query",
                    value = "Filtering criteria for field `methodType`. (omitted if null)"),
            @ApiImplicitParam(name = "responseDate<", dataType = "date", paramType = "query",
                    value = "Filtering criteria for field `responseDate`. (omitted if null)"),
            @ApiImplicitParam(name = "responseDate>", dataType = "date", paramType = "query",
                    value = "Filtering criteria for field `responseDate`. (omitted if null)"),
            @ApiImplicitParam(name = "usersLoggedIn>", dataType = "integer", paramType = "query",
                    value = "Filtering criteria for field `usersLoggedIn`. (omitted if null)"),
            @ApiImplicitParam(name = "usersLoggedIn<", dataType = "integer", paramType = "query",
                    value = "Filtering criteria for field `usersLoggedIn`. (omitted if null)"),
            @ApiImplicitParam(name = "requestResponseTime<", dataType = "integer", paramType = "query",
                    value = "Filtering criteria for field `requestResponseTime`. (omitted if null)"),
            @ApiImplicitParam(name = "requestResponseTime>", dataType = "integer", paramType = "query",
                    value = "Filtering criteria for field `requestResponseTime`. (omitted if null)"),
            @ApiImplicitParam(name = "operationTime<", dataType = "integer", paramType = "query",
                    value = "Filtering criteria for field `operationTime`. (omitted if null)"),
            @ApiImplicitParam(name = "operationTime>", dataType = "integer", paramType = "query",
                    value = "Filtering criteria for field `operationTime`. (omitted if null)"),
    })
    public Page<ResponseDTO> getResponses(@ApiIgnore Pageable pageable, ResponseSpecification responseSpecification) {
        return responseService.getResponses(pageable,responseSpecification)
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
