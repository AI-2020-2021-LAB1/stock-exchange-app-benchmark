package org.project.benchmark.app.dto;

import lombok.Getter;
import lombok.Setter;
import org.project.benchmark.app.entity.TestStatus;

@Getter
@Setter
public class TestProgressDTO {
    private Long id;
    private double progress;
    private TestStatus status;
}
