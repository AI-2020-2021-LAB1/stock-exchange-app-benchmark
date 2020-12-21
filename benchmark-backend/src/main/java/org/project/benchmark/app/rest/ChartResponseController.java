package org.project.benchmark.app.rest;


import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.project.benchmark.app.dto.ChartResponseDTO;
import org.project.benchmark.app.service.ChartResponseServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chartResponse")
@AllArgsConstructor
@Api(value = "ChartResponses", description = "REST API for chart response's management by test ID", tags = " Chart responses")
@CrossOrigin("*")
public class ChartResponseController {

    private final ChartResponseServiceImpl chartResponseService;

    @GetMapping("/{testId}/endpoint")
    @ApiOperation(value = "Successfully retrieve chart Responses for all endpoints")
    @ApiResponses(@ApiResponse(code = 200, message = " Successfully get list of endpoints."))
    public ChartResponseDTO getResponseTimeForAllEndpoints(@ApiParam(value = "Id of desired Response",
            required = true)@PathVariable Long testId) {
        return chartResponseService.getChartResponseForAllEndpoints(testId);
    }

    @GetMapping("/{testId}/method")
    @ApiOperation(value = "Successfully retrieve chart Responses for all methods")
    @ApiResponses(@ApiResponse(code = 200, message = " Successfully get list of method."))
    public ChartResponseDTO getResponsesTimeForAllMethod(@ApiParam(value = "Id of desired Response",
            required = true) @PathVariable Long testId) {
        return chartResponseService.getChartResponseForAllMethod(testId);
    }

}
