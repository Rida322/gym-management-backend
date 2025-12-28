package com.gym.gym_backend.service;

import com.gym.gym_backend.dto.AIWorkoutRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.*;
@Service
public class AIWorkoutService {

    @Value("${openai.api.key}")
    private String apiKey;

    public String generateWorkout(AIWorkoutRequest req) {

        String prompt = """
        You are a professional gym coach.
        Create a 7-day gym workout plan.

        Rules:
        - ONLY gym equipment
        - No bodyweight workouts
        - Include sets x reps

        User:
        Gender: %s
        Age: %d
        Weight: %.1f kg
        Height: %.1f cm
        Goal: %s
        Level: %s
        """.formatted(
                req.gender,
                req.age,
                req.weight,
                req.height,
                req.goal,
                req.level
        );

        try {
            RestTemplate rest = new RestTemplate();

            Map<String, Object> body = new HashMap<>();
            body.put("model", "gpt-3.5-turbo");
            body.put("messages", List.of(
                    Map.of("role", "user", "content", prompt)
            ));

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> entity =
                    new HttpEntity<>(body, headers);

            Map<?, ?> response = rest.postForObject(
                    "https://api.openai.com/v1/chat/completions",
                    entity,
                    Map.class
            );

            // 🔐 SAFE CHECKS (NO MORE 500)
            if (response == null) {
                return "AI service returned no response.";
            }

            if (response.containsKey("error")) {
                return "AI error: " + response.get("error").toString();
            }

            List<?> choices = (List<?>) response.get("choices");
            if (choices == null || choices.isEmpty()) {
                return "AI returned empty result.";
            }

            Map<?, ?> message =
                    (Map<?, ?>) ((Map<?, ?>) choices.get(0)).get("message");

            return message.get("content").toString();

        } catch (Exception e) {
            System.err.println("OPENAI ERROR:");
            System.err.println(e.getMessage());
            return "AI service failed. Please try again later.";
        }

    }
}

