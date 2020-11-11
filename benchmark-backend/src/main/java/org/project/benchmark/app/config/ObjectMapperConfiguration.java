package org.project.benchmark.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfiguration {
    @Bean
    ObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();
        //additional config
        return mapper;
    }
}
