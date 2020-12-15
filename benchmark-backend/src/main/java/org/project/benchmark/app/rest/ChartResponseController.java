package org.project.benchmark.app.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.project.benchmark.app.dto.ChartResponseDTO;
import org.project.benchmark.app.service.ChartResponseServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chartResponse")
@AllArgsConstructor
@Api(value = "ChartResponses", description = "REST API for chart response's management", tags = " Chart responses")
@CrossOrigin("*")
public class ChartResponseController {

    private final ChartResponseServiceImpl chartResponseService;

    @GetMapping("/endpoint")
    @ApiOperation(value = "Successfully retrieve chart Responses for all endpoints")
    @ApiResponses(@ApiResponse(code = 200, message = " Successfully get list of endpoints."))
    public ChartResponseDTO getResponseTimeForAllEndpoints() {
        return chartResponseService.getChartResponseForAllEndpoints();
    }

    @GetMapping("/method")
    @ApiOperation(value = "Successfully retrieve chart Responses for all methods")
    @ApiResponses(@ApiResponse(code = 200, message = " Successfully get list of method."))
    public ChartResponseDTO getResponsesTimeForAllMethod() {
        return chartResponseService.getChartResponseForAllMethod();
    }

}
