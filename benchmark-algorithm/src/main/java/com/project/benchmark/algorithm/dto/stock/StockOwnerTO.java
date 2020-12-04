package com.project.benchmark.algorithm.dto.stock;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockOwnerTO {
    private Integer amount;
    private StockUserTO user;
}
