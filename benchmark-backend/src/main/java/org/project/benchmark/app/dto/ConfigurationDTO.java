package org.project.benchmark.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

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

    @ApiModelProperty(notes = "The configuration's certainty level")
    private BigDecimal certaintyLevel;

    @ApiModelProperty(notes = "The configuration's loginAllStocks value")
    private BigDecimal loginAllStocks;

    @ApiModelProperty(notes = "The configuration's loginOwnedStocks value")
    private BigDecimal loginOwnedStocks;

    @ApiModelProperty(notes = "The configuration's loginUserOrders value")
    private BigDecimal loginUserOrders;

    @ApiModelProperty(notes = "The configuration's loginMakeOrder value")
    private BigDecimal loginMakeOrder;

    @ApiModelProperty(notes = "The configuration's allStocksMakeOrder value")
    private BigDecimal allStocksMakeOrder;

    @ApiModelProperty(notes = "The configuration's allStocksEnd value")
    private BigDecimal allStocksEnd;

    @ApiModelProperty(notes = "The configuration's ownedStocksMakeOrder value")
    private BigDecimal ownedStocksMakeOrder;

    @ApiModelProperty(notes = "The configuration's ownedStocksEnd value")
    private BigDecimal ownedStocksEnd;

    @ApiModelProperty(notes = "The configuration's userOrdersMakeOrder value")
    private BigDecimal userOrdersMakeOrder;

    @ApiModelProperty(notes = "The configuration's userOrdersEnd value")
    private BigDecimal userOrdersEnd;

    @ApiModelProperty(notes = "The configuration's userOrderDeleteOrder value")
    private BigDecimal userOrderDeleteOrder;

    @ApiModelProperty(notes = "The configuration's makeOrderBuyOrder value")
    private BigDecimal makeOrderBuyOrder;

    @ApiModelProperty(notes = "The configuration's makeOrderSellOrder value")
    private BigDecimal makeOrderSellOrder;

    @ApiModelProperty(notes = "The configuration's noOfOperations value")
    private BigDecimal noOfOperations;

    @ApiModelProperty(notes = "The configuration's noOfMoney value")
    private BigDecimal noOfMoney;
}
