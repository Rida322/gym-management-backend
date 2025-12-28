package com.gym.gym_backend.repo;

import com.gym.gym_backend.entity.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Integer> {
    List<WorkoutPlan> findByUserIdOrderByCreatedAtDesc(Integer userId);
}
