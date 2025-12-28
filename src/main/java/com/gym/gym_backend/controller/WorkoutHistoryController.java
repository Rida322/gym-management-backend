package com.gym.gym_backend.controller;

import com.gym.gym_backend.entity.WorkoutHistory;
import com.gym.gym_backend.repo.WorkoutHistoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member")
@CrossOrigin(origins = "*")
public class WorkoutHistoryController {

    private final WorkoutHistoryRepository repo;

    public WorkoutHistoryController(WorkoutHistoryRepository repo) {
        this.repo = repo;
    }

    // ✅ THIS ENDPOINT IS WHAT FLUTTER CALLS
    @GetMapping("/workout-history/{userId}")
    public List<WorkoutHistory> history(@PathVariable Integer userId) {

        System.out.println("FETCH WORKOUT HISTORY FOR USER: " + userId);

        List<WorkoutHistory> list = repo.findByUserIdOrderByWorkoutDateDesc(userId);

        System.out.println("ROWS FOUND: " + list.size());

        return list;
    }
}
