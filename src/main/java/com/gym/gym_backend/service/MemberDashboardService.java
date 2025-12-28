package com.gym.gym_backend.service;

import com.gym.gym_backend.dto.MemberDashboardDTO;
import com.gym.gym_backend.entity.*;
import com.gym.gym_backend.repo.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class MemberDashboardService {

    private final MemberInfoRepository infoRepo;
    private final MemberProgressRepository progressRepo;
    private final WorkoutHistoryRepository workoutRepo;
    private final GymScheduleRepository scheduleRepo;
    private final UserRepository userRepo;

    public MemberDashboardService(
            MemberInfoRepository infoRepo,
            MemberProgressRepository progressRepo,
            WorkoutHistoryRepository workoutRepo,
            GymScheduleRepository scheduleRepo,
            UserRepository userRepo
    ) {
        this.infoRepo = infoRepo;
        this.progressRepo = progressRepo;
        this.workoutRepo = workoutRepo;
        this.scheduleRepo = scheduleRepo;
        this.userRepo = userRepo;
    }

    public MemberDashboardDTO getDashboard(Integer userId) {

        // ✅ User MUST exist
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✅ Member info MAY NOT exist
        MemberInfo info = infoRepo.findByUserId(userId).orElse(null);

        double weight = info != null ? info.getWeight() : 0;
        int height = info != null ? info.getHeight() : 0;
        String experience = info != null ? info.getExperience() : "Beginner";

        double bmi = (height > 0)
                ? weight / Math.pow(height / 100.0, 2)
                : 0;

        // ✅ Weight history (safe)
        List<Double> weights = progressRepo
                .findByUserIdOrderByProgressDateAsc(userId)
                .stream()
                .map(MemberProgress::getWeight)
                .toList();

        // ✅ Crowd data (safe)
        Map<String, Integer> crowd = new HashMap<>();
        for (Object[] row : scheduleRepo.countCrowdToday()) {
            String time = row[0].toString();
            if (time.length() >= 5) time = time.substring(0, 5);
            crowd.put(time, ((Long) row[1]).intValue());
        }

        // ✅ Dashboard DTO
        MemberDashboardDTO dto = new MemberDashboardDTO();
        dto.name = user.getName();
        dto.weight = weight;
        dto.height = height;
        dto.bmi = bmi;
        dto.totalWorkouts = workoutRepo.totalWorkouts(userId);
        dto.workoutsThisWeek =
                workoutRepo.workoutsThisWeek(userId, LocalDate.now().minusDays(7));
        dto.experience = experience;
        dto.weightHistory = weights;
        dto.crowdData = crowd;

        return dto;
    }
}
