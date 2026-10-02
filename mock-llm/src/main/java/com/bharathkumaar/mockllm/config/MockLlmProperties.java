package com.bharathkumaar.mockllm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mock.llm")
public record MockLlmProperties(
        long latencyMs,
        double errorRate,
        int inputTokens,
        int outputTokens
) {
}