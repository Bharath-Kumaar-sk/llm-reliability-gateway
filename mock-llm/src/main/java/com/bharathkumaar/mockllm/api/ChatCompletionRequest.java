package com.bharathkumaar.mockllm.api;

import java.util.List;

public record ChatCompletionRequest(
        String model,
        List<Message> messages
) {
    public record Message(
            String role,
            String content
    ) {}
}