package com.project.benchmark.algorithm.dto.order;

import com.project.benchmark.algorithm.dto.stock.StockTO;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class OrderTO {
    private Long amount;
    private OffsetDateTime dateClosing;
    private OffsetDateTime dateCreation;
    private OffsetDateTime dateExpiration;
    private Integer id;
    private String orderType;
    private Double price;
    private String priceType;
    private Long remainingAmount;
    private StockTO stock;
}
