package com.project.benchmark.algorithm.dto.stock;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewStockOwnerTO {
    private Integer amount;
    private NewStockUserTO user;
}
