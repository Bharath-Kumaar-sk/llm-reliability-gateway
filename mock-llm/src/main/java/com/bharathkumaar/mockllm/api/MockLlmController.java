package com.bharathkumaar.mockllm.api;

import com.bharathkumaar.mockllm.config.MockLlmProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/v1")
public class MockLlmController {

    private final MockLlmProperties properties;

    public MockLlmController(MockLlmProperties properties) {
        this.properties = properties;
    }

    @PostMapping("/chat/completions")
    public ResponseEntity<?> chatCompletions(
            @RequestBody ChatCompletionRequest request) {

        simulateLatency();

        if (shouldFail()) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("""
                            {
                              "error": {
                                "message": "Simulated mock LLM failure",
                                "type": "mock_error"
                              }
                            }
                            """);
        }

        String model = request.model() != null
                ? request.model()
                : "mock-llm";

        ChatCompletionResponse response =
                new ChatCompletionResponse(
                        "chatcmpl-" + UUID.randomUUID(),
                        "chat.completion",
                        model,
                        List.of(
                                new ChatCompletionResponse.Choice(
                                        0,
                                        new ChatCompletionResponse.Message(
                                                "assistant",
                                                "Mock LLM response"
                                        ),
                                        "stop"
                                )
                        ),
                        new ChatCompletionResponse.Usage(
                                properties.inputTokens(),
                                properties.outputTokens(),
                                properties.inputTokens()
                                        + properties.outputTokens()
                        )
                );

        return ResponseEntity.ok(response);
    }

    private void simulateLatency() {
        if (properties.latencyMs() <= 0) {
            return;
        }

        try {
            Thread.sleep(properties.latencyMs());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private boolean shouldFail() {
        return ThreadLocalRandom.current()
                .nextDouble() < properties.errorRate();
    }
}