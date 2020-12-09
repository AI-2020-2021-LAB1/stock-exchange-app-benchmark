package org.project.benchmark.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@EnableScheduling
@ConfigurationPropertiesScan("org.project.benchmark.app.config.properties")
public class BenchmarkApplication {

    public static void main(String[] args) {
        SpringApplication.run(BenchmarkApplication.class, args);
    }
}
