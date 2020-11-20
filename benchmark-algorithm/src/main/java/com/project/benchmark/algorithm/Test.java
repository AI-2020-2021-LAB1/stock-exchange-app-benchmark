package com.project.benchmark.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class Test {
    private long id;
    private long configurationId;
    private Timestamp startDate;
    private Timestamp endDate;
}
