package com.gym.gym_backend.repo;

import com.gym.gym_backend.entity.WorkoutHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface WorkoutHistoryRepository extends JpaRepository<WorkoutHistory, Integer> {

    @Query("SELECT COUNT(w) FROM WorkoutHistory w WHERE w.userId = :userId")
    int totalWorkouts(Integer userId);

    @Query("""
      SELECT COUNT(w) FROM WorkoutHistory w
      WHERE w.userId = :userId AND w.workoutDate >= :start
    """)
    int workoutsThisWeek(Integer userId, LocalDate start);
    List<WorkoutHistory> findByUserIdOrderByWorkoutDateDesc(Integer userId);


    long countByUserId(Integer userId);

    long countByUserIdAndWorkoutDateAfter(Integer userId, java.time.LocalDate date);
}

