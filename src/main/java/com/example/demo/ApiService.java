package com.example.demo;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class ApiService {
    private final WebClient webClient;

    public ApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:11434").build();
    }

    public Flux<String> getResponse(String prompt) {
        // Directly embedding the prompt in the JSON request body
//        String requestBody = "{\"model\": \"llama3\", \"prompt\": \"Introduce yourself in 10 words\", \"stream\": true}";
        ChatStructure requestBody = new ChatStructure();
        requestBody.setModel("llama3");
        requestBody.setPrompt(prompt);
        requestBody.setStream(true);

        Flux<String> responseFlux = webClient.post()
                .uri("/api/generate")
                .header("Content-Type", "application/json")
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToFlux(String.class)
                .map(this::extractContent)
                .filter(content -> content != null && !content.isEmpty())
                .map(content -> content.replaceAll("\\\\n", "\n"));

        return responseFlux;
    }

    private String extractContent(String response) {
        int startIndex = response.indexOf("\"response\":");
        if (startIndex != -1) { // Check if "content" is found
            startIndex = response.indexOf("\"", startIndex + "\"response\":".length()); // Find the opening quote after "content"
            if (startIndex != -1) {
                int endIndex = response.indexOf("\"", startIndex + 1); // Find the closing quote after content value
                if (endIndex != -1) {
                    return response.substring(startIndex + 1, endIndex); // Add 1 to startIndex to remove the opening quote
                }
            }
        }
        return null; // Return null if content is not found or if the indexes are invalid
    }
}
