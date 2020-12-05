package org.project.benchmark.app.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.project.benchmark.app.dto.ErrorResponse;
import org.project.benchmark.app.dto.ResponseDTO;
import org.project.benchmark.app.dto.TestDTO;
import org.project.benchmark.app.service.ResponseService;
import org.project.benchmark.app.service.TestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/test")
@AllArgsConstructor
@Api(value = "Tests", description = "REST API for test's management", tags = "Tests")
@CrossOrigin("*")
public class TestController {

    private final TestService testService;
    private final ResponseService responseService;
    private final ObjectMapper mapper;

    @GetMapping
    @ApiOperation(value = "Page tests.")
    @ApiResponses(@ApiResponse(code = 200, message = " Successfully paged tests."))
    public Page<TestDTO> getTests(@ApiIgnore Pageable pageable) {
        return testService.getTests(pageable)
                .map(test -> mapper.convertValue(test,TestDTO.class));
    }

    @PostMapping
    @ApiOperation(value = "Create new Test")
    @ApiResponses({@ApiResponse(code = 200, message = "Test was successfully created."),
            @ApiResponse(code = 400, message = "The request could not be understood or was missing required parameters.",
                    response = ErrorResponse.class)})
    public void create(@RequestBody @Valid TestDTO testDTO) {
        testService.createTest(testDTO);
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieve test by id", response = TestDTO.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Test was successfully retrieved."),
            @ApiResponse(code = 404, message = "Given test not found.", response = ErrorResponse.class)})
    public TestDTO getTestDetails(@ApiParam(value = "Id of desired Test", required = true)
                                  @PathVariable Long id) {
        return mapper.convertValue(testService.getTestByID(id),
                TestDTO.class);
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Update test")
    @ApiResponses({@ApiResponse(code = 200, message = "Test was successfully updated."),
            @ApiResponse(code = 404, message = "Given test not found.", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "The request could not be understood or was missing required parameters.",
                    response = ErrorResponse.class)})
    public void updateTest(
            @ApiParam(value = "Test object to update.",
                    required = true) @Valid @RequestBody TestDTO testDTO,
            @ApiParam(value = "Id of desired test", required = true) @PathVariable Long id) {
        testService.updateTest(testDTO, id);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete existing test")
    @ApiResponses({@ApiResponse(code = 200, message = "Test was successfully deleted."),
            @ApiResponse(code = 404, message = "Given test not found.", response = ErrorResponse.class)})
    public void delete(@ApiParam(value = "The id of test to delete.", required = true) @PathVariable Long id) {
        testService.deleteTest(id);
    }

    //SPECYFIKACJA ?
    @GetMapping("/{id}/responses")
    @ApiOperation(value = "Page given test's responses")
    @ApiResponses({@ApiResponse(code = 200, message = "Successfully paged test's responses."),
            @ApiResponse(code = 404, message = "Given test not found.", response = ErrorResponse.class)
    })
    public Page<ResponseDTO> getTestResponses(@ApiParam(value = "The test's id",  required = true) @PathVariable Long id,
                                              @ApiIgnore Pageable pageable) {
        return responseService.getTestResponses(id,pageable)
                .map(response -> mapper.convertValue(response,ResponseDTO.class));
    }

}
