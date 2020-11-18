package com.project.benchmark.algorithm.dto.stock;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockUserTO {
    private String email;
    private String firstName;
    private Integer id;
    private String lastName;
    private Double money;
    private String role;
}
