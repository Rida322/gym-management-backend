package com.gym.gym_backend.dto;

public class SaveWorkoutRequest {
    private Integer userId;
    private String plan;
    private String workoutName;

    private Double weight;
    private Integer height;
    private String experience;

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getPlan() { return plan; }
    public void setPlan(String plan) { this.plan = plan; }

    public String getWorkoutName() { return workoutName; }
    public void setWorkoutName(String workoutName) { this.workoutName = workoutName; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }

    public Integer getHeight() { return height; }
    public void setHeight(Integer height) { this.height = height; }

    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }
}
