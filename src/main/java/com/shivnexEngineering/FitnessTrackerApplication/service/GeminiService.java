package com.shivnexEngineering.FitnessTrackerApplication.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shivnexEngineering.FitnessTrackerApplication.dto.ActivityGeminiRequest;
import com.shivnexEngineering.FitnessTrackerApplication.dto.GeminiRecommendationResponse;
import com.shivnexEngineering.FitnessTrackerApplication.entity.Activity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public GeminiRecommendationResponse generateRecommendation(Activity activity){

        ActivityGeminiRequest activityGeminiRequest = new ActivityGeminiRequest(
            activity.getType(), activity.getDuration(), activity.getCaloriesBurned(), activity.getAdditionalMetrics()
        );

        String activityJson;

        try {
            activityJson = objectMapper.writeValueAsString(activityGeminiRequest);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert activity to JSON", e);
        }

        String prompt = """
            You are a professional fitness coach.

            Analyze this activity:

            %s

            Return ONLY a valid JSON object.

            Do not use markdown.
            Do not use code blocks.
            Do not provide explanations.

            Expected format:

            {
            "recommendation": "string",
            "improvements": ["string"],
            "suggestions": ["string"],
            "safety": ["string"]
            }
            """
            .formatted(activityJson);

        Map<String, Object> request = buildRequest(prompt);    

        String response = restClient.post()
            .uri(
                "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key="
                + apiKey
            )
            .body(request)
            .retrieve()
            .body(String.class);

        return parseGeminiResponse(response); 

    }

    private Map<String, Object> buildRequest(String prompt) {

        return Map.of(
            "contents",
            List.of(
                Map.of(
                    "parts",
                    List.of(
                        Map.of("text", prompt)
                    )
                )
            )
        );

    }

    private GeminiRecommendationResponse parseGeminiResponse(String response) {

        try {

            JsonNode root = objectMapper.readTree(response);

            String generatedJson =
                    root.path("candidates")
                        .get(0)
                        .path("content")
                        .path("parts")
                        .get(0)
                        .path("text")
                        .asText();

            generatedJson = generatedJson
                    .replace("```json", "")
                    .replace("```", "")
                    .trim();

            return objectMapper.readValue(
                    generatedJson,
                    GeminiRecommendationResponse.class);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to parse Gemini response",
                    e);
        }
    }

}
