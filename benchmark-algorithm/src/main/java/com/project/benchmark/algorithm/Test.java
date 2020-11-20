package com.project.benchmark.algorithm;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class Test {
    private long id;
    private long configurationId;
    private Timestamp startDate;
    private Timestamp endDate;
}
