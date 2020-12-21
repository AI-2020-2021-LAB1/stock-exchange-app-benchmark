package org.project.benchmark.app.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.benchmark.app.util.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@ConfigurationProperties(prefix = "benchmark.algorithm.core")
@Validated
public class CoreProperties {
    /**
     * Algorithm threads used to run algorithm iteration per user.
     * Default 256 initial threads and 512 max threads,
     * but should be based on server capabilities.
     */
    @NotNull
    @Valid
    private ThreadProperties algorithmThreads;
    /**
     * Core threads used to manage algorithm iterations per user.
     * Default 8, but should be based on algorithmThreads property.
     */
    @Positive
    private int coreThreads = 8;

    @NotBlank(message = "Value cannot be blank")
    @URL
    private String stockBackendAddress;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ThreadProperties {
        /**
         * Minimal value
         */
        @Positive
        private int min = 256;
        /**
         * Max value
         */
        @Positive
        private int max = 512;

        public boolean isValid() {
            return min <= max;
        }
    }


}
