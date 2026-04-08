package com.ashish.email.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class EmailGeneratorService {

    private final WebClient webClient;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public EmailGeneratorService(WebClient webClient) {
        this.webClient = webClient;
    }

//    public String generateEmailReply(EmailRequest emailRequest) {
//        // 1. Build the prompt
//        String prompt = buildPrompt(emailRequest);
//
//        // 2. Craft the request body for Gemini API
//        Map<String, Object> requestBody = Map.of(
//                "contents", new Object[] {
//                        Map.of("parts", new Object[]{
//                                Map.of("text", prompt)
//                        })
//                }
//        );
//
//        // DEBUG LINE: Add this to see exactly what is happening in the console
//        String finalUrl = geminiApiUrl + geminiApiKey;
//
//
//        // 3. Make the API Call
//        String response = webClient.post()
//                .uri(geminiApiUrl + geminiApiKey)
//                .header("Content-Type", "application/json")
//                .bodyValue(requestBody)
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
//
//        // 4. Extract and return the specific text content
//        return extractResponseContent(response);
//    }
public String generateEmailReply(EmailRequest emailRequest) {
    String prompt = buildPrompt(emailRequest);

    Map<String, Object> requestBody = Map.of(
            "contents", new Object[] {
                    Map.of("parts", new Object[]{
                            Map.of("text", prompt)
                    })
            }
    );

    String finalUrl = geminiApiUrl + geminiApiKey;

    try {
        return webClient.post()
                .uri(finalUrl)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(status -> status.isError(), clientResponse ->
                        clientResponse.bodyToMono(String.class).flatMap(errorBody -> {
                            System.out.println("GOOGLE API ERROR BODY: " + errorBody);
                            return reactor.core.publisher.Mono.error(new RuntimeException("Google API Error: " + errorBody));
                        })
                )
                .bodyToMono(String.class)
                .block();
    } catch (Exception e) {
        return "Failed: " + e.getMessage();
    }
}
    private String extractResponseContent(String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);
            return rootNode.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();
        } catch (Exception e) {
            return "Error processing request: " + e.getMessage();
        }
    }

    private String buildPrompt(EmailRequest emailRequest) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Generate a professional email reply for the following email content. Please don't generate a subject line. ");
        if (emailRequest.getTone() != null && !emailRequest.getTone().isEmpty()) {
            prompt.append("Use a ").append(emailRequest.getTone()).append(" tone.");
        }
        prompt.append("\nOriginal email: \n").append(emailRequest.getEmailContent());
        return prompt.toString();
    }
}