package com.gym.gym_backend.controller;

import com.gym.gym_backend.entity.WorkoutPlan;
import com.gym.gym_backend.repo.WorkoutPlanRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member")
@CrossOrigin(origins = "*")
public class WorkoutPlanController {

    private final WorkoutPlanRepository repo;

    public WorkoutPlanController(WorkoutPlanRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/workout-plan/{userId}")
    public List<WorkoutPlan> plans(@PathVariable Integer userId) {
        return repo.findByUserIdOrderByCreatedAtDesc(userId);
    }
}
