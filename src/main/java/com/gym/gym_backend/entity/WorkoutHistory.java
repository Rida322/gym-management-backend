package com.gym.gym_backend.entity;
import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
@Table(name = "workout_history")
public class WorkoutHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    private String workoutName;
    private Integer calories;
    private LocalDate workoutDate;

    public Integer getUserId() {
        return userId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCalories() {
        return calories;
    }

    public LocalDate getWorkoutDate() {
        return workoutDate;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public void setWorkoutDate(LocalDate workoutDate) {
        this.workoutDate = workoutDate;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }
}
