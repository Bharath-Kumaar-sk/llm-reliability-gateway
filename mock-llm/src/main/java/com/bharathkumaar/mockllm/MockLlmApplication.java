package com.bharathkumaar.mockllm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MockLlmApplication {

    public static void main(String[] args) {
        SpringApplication.run(MockLlmApplication.class, args);
    }
}