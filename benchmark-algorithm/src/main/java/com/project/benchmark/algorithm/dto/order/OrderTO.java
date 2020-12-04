package com.project.benchmark.algorithm.dto.order;

import com.project.benchmark.algorithm.dto.stock.StockTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OrderTO {
    private Long amount;
    private Date dateClosing; //java.util.Date, możliwe do zmiany
    private Date dateCreation; //java.util.Date, możliwe do zmiany
    private Date dateExpiration; //java.util.Date, możliwe do zmiany
    private Integer id;
    private String orderType;
    private Double price;
    private String priceType;
    private Double remainingAmount;
    private StockTO stock;
}
