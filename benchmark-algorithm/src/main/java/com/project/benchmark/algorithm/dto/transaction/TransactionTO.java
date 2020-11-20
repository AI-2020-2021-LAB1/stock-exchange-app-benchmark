package com.project.benchmark.algorithm.dto.transaction;

import lombok.Data;

import com.project.benchmark.algorithm.dto.order.OrderTO;

import java.time.OffsetDateTime;

@Data
public class TransactionTO {
    private long amount;
    private OrderTO buyingOrder;
    private OffsetDateTime date;
    private Integer id;
    private OrderTO sellingOrder;
    private Double unitPrice;
}
