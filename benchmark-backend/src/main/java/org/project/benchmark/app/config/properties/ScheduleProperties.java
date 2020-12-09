package org.project.benchmark.app.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Positive;

@Getter
@Setter
@ConfigurationProperties(prefix = "benchmark.algorithm.scheduler")
@Validated
public class ScheduleProperties {
    /**
     * specifies interval for saving responses to database in milliseconds.
     * Default 5000ms.
     */
    @Positive
    private int saveInterval = 5000;
    /**
     * Thread count used for saving responses.
     * Not yet used.
     */
    private Integer threadCount;

    /**
     * Limit for saved events. If not specified, no limit will be thrown.
     * Not yet used.
     */
    private Integer eventsLimitPerThread;
}
