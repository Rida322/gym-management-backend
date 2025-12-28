package com.gym.gym_backend.controller;

import com.gym.gym_backend.dto.AIWorkoutRequest;
import com.gym.gym_backend.dto.AIWorkoutResponse;
import com.gym.gym_backend.service.AIWorkoutService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member/ai-workout")
@CrossOrigin(origins = "*")
public class AIWorkoutController {

    private final AIWorkoutService service;

    public AIWorkoutController(AIWorkoutService service) {
        this.service = service;
    }

    @PostMapping
    public AIWorkoutResponse generate(@RequestBody AIWorkoutRequest request) {

        String plan = service.generateWorkout(request);

        AIWorkoutResponse res = new AIWorkoutResponse();
        res.workoutPlan = plan;
        return res;
    }
}
