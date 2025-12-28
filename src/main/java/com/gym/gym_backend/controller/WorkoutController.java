package com.gym.gym_backend.controller;

import com.gym.gym_backend.dto.SaveWorkoutRequest;
import com.gym.gym_backend.entity.MemberInfo;
import com.gym.gym_backend.entity.WorkoutHistory;
import com.gym.gym_backend.entity.WorkoutPlan;
import com.gym.gym_backend.repo.MemberInfoRepository;
import com.gym.gym_backend.repo.WorkoutHistoryRepository;
import com.gym.gym_backend.repo.WorkoutPlanRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/member/workout")
@CrossOrigin(origins = "*")
public class WorkoutController {

    private final WorkoutPlanRepository planRepo;
    private final WorkoutHistoryRepository historyRepo;
    private final MemberInfoRepository infoRepo;

    public WorkoutController(
            WorkoutPlanRepository planRepo,
            WorkoutHistoryRepository historyRepo,
            MemberInfoRepository infoRepo
    ) {
        this.planRepo = planRepo;
        this.historyRepo = historyRepo;
        this.infoRepo = infoRepo;
    }

    // ✅ SAVE AI WORKOUT (WorkoutPlan + WorkoutHistory + MemberInfo)
    @PostMapping("/save")
    public void saveWorkout(@RequestBody SaveWorkoutRequest req) {

        // 1) Update MemberInfo so Dashboard/Progress cards change ✅
        MemberInfo info = infoRepo.findByUserId(req.getUserId()).orElse(null);

        if (info == null) {
            info = new MemberInfo();
            info.setUserId(req.getUserId());
        }

        info.setWeight(req.getWeight());
        info.setHeight(req.getHeight());
        info.setExperience(req.getExperience());

        infoRepo.save(info);

        // 2) Save AI plan text ✅
        WorkoutPlan plan = new WorkoutPlan();
        plan.setUserId(req.getUserId());
        plan.setPlan(req.getPlan());
        planRepo.save(plan);

        // 3) Save history row with workout name ✅
        WorkoutHistory history = new WorkoutHistory();
        history.setUserId(req.getUserId());
        history.setWorkoutName(req.getWorkoutName()); // ✅ from Flutter field
        history.setCalories(0);
        history.setWorkoutDate(LocalDate.now());
        historyRepo.save(history);
    }
}
