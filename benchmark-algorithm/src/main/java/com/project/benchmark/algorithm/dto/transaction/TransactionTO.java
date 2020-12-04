package com.project.benchmark.algorithm.dto.transaction;

import lombok.Getter;
import lombok.Setter;

import com.project.benchmark.algorithm.dto.order.OrderTO;
import java.util.Date;

@Getter
@Setter
public class TransactionTO {
    private long amount;
    private OrderTO buyingOrder;
    private Date date;
    private Integer id;
    private OrderTO sellingOrder;
    private Double unitPrice;
}
