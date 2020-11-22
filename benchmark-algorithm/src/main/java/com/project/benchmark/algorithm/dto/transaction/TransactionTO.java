package com.project.benchmark.algorithm.dto.transaction;

import lombok.Getter;
import lombok.Setter;

import com.project.benchmark.algorithm.dto.order.OrderTO;

import java.time.OffsetDateTime;

@Getter
@Setter
public class TransactionTO {
    private Long amount;
    private OrderTO buyingOrder;
    private OffsetDateTime date;
    private Integer id;
    private OrderTO sellingOrder;
    private Double unitPrice;
}
