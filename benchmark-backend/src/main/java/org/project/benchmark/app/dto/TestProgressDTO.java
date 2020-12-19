package org.project.benchmark.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.benchmark.app.entity.TestStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestProgressDTO {
    private Long id;
    private double progress;
    private TestStatus status;
}
