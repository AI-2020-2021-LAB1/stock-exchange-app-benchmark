package org.project.benchmark.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.benchmark.app.util.ConfigurationPercentage;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@ConfigurationPercentage
@JsonIgnoreProperties(value = { "tests" })
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
@ApiModel(description = "Configuration object stored in database.")
public class ConfigurationDTO {

    @ApiModelProperty(notes = "The configuration's id.")
    private Long id;

    @ApiModelProperty(notes = "The configuration's name.")
    @NotBlank(message = "This field is required.")
    private String name;

    @ApiModelProperty(notes = "The configuration's creation date.")
    private OffsetDateTime createdAt;

    @ApiModelProperty(notes = "The configuration's archiving status")
    private boolean archived;

    @Min(value=1, message="must be equal or greater than 1")
    @Max(value=100, message="must be equal or lower than 100")
    @ApiModelProperty(notes = "The configuration's loginAllStocks value")
    private BigDecimal loginAllStocks;

    @Min(value=1, message="must be equal or greater than 1")
    @Max(value=100, message="must be equal or lower than 100")
    @ApiModelProperty(notes = "The configuration's loginOwnedStocks value")
    private BigDecimal loginOwnedStocks;

    @Min(value=1, message="must be equal or greater than 1")
    @Max(value=100, message="must be equal or lower than 100")
    @ApiModelProperty(notes = "The configuration's loginUserOrders value")
    private BigDecimal loginUserOrders;

    @Min(value=1, message="must be equal or greater than 1")
    @Max(value=100, message="must be equal or lower than 100")
    @ApiModelProperty(notes = "The configuration's loginMakeOrder value")
    private BigDecimal loginMakeOrder;

    @Min(value=1, message="must be equal or greater than 1")
    @Max(value=100, message="must be equal or lower than 100")
    @ApiModelProperty(notes = "The configuration's allStocksMakeOrder value")
    private BigDecimal allStocksMakeOrder;

    @Min(value=1, message="must be equal or greater than 1")
    @Max(value=100, message="must be equal or lower than 100")
    @ApiModelProperty(notes = "The configuration's allStocksEnd value")
    private BigDecimal allStocksEnd;

    @Min(value=1, message="must be equal or greater than 1")
    @Max(value=100, message="must be equal or lower than 100")
    @ApiModelProperty(notes = "The configuration's ownedStocksMakeOrder value")
    private BigDecimal ownedStocksMakeOrder;

    @Min(value=1, message="must be equal or greater than 1")
    @Max(value=100, message="must be equal or lower than 100")
    @ApiModelProperty(notes = "The configuration's ownedStocksEnd value")
    private BigDecimal ownedStocksEnd;

    @Min(value=1, message="must be equal or greater than 1")
    @Max(value=100, message="must be equal or lower than 100")
    @ApiModelProperty(notes = "The configuration's userOrdersMakeOrder value")
    private BigDecimal userOrdersMakeOrder;

    @Min(value=1, message="must be equal or greater than 1")
    @Max(value=100, message="must be equal or lower than 100")
    @ApiModelProperty(notes = "The configuration's userOrdersEnd value")
    private BigDecimal userOrdersEnd;

    @Min(value=1, message="must be equal or greater than 1")
    @Max(value=100, message="must be equal or lower than 100")
    @ApiModelProperty(notes = "The configuration's userOrderDeleteOrder value")
    private BigDecimal userOrderDeleteOrder;

    @Min(value=1, message="must be equal or greater than 1")
    @Max(value=100, message="must be equal or lower than 100")
    @ApiModelProperty(notes = "The configuration's makeOrderBuyOrder value")
    private BigDecimal makeOrderBuyOrder;

    @Min(value=1, message="must be equal or greater than 1")
    @Max(value=100, message="must be equal or lower than 100")
    @ApiModelProperty(notes = "The configuration's makeOrderSellOrder value")
    private BigDecimal makeOrderSellOrder;

    @Min(value=1, message="must be equal or greater than 1")
    @ApiModelProperty(notes = "The configuration's noOfOperations value")
    private BigDecimal noOfOperations;

    @Min(value=1, message="must be equal or greater than 1")
    @ApiModelProperty(notes = "The configuration's noOfMoney value")
    private BigDecimal noOfMoney;
}
