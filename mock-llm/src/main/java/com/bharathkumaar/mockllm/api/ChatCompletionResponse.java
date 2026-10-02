package com.bharathkumaar.mockllm.api;

import java.util.List;

public record ChatCompletionResponse(
        String id,
        String object,
        String model,
        List<Choice> choices,
        Usage usage
) {
    public record Choice(
            int index,
            Message message,
            String finishReason
    ) {}

    public record Message(
            String role,
            String content
    ) {}

    public record Usage(
            int promptTokens,
            int completionTokens,
            int totalTokens
    ) {}
}