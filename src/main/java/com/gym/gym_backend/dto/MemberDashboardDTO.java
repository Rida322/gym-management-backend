package com.gym.gym_backend.dto;

import java.util.List;
import java.util.Map;

public class MemberDashboardDTO {

    public String name;
    public Double weight;
    public Integer height;
    public Double bmi;
    public int totalWorkouts;
    public int workoutsThisWeek;
    public String experience;
    public List<Double> weightHistory;
    public Map<String, Integer> crowdData;

    public String getName() {
        return name;
    }

    public Integer getHeight() {
        return height;
    }

    public Double getWeight() {
        return weight;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getBmi() {
        return bmi;
    }

    public int getTotalWorkouts() {
        return totalWorkouts;
    }

    public int getWorkoutsThisWeek() {
        return workoutsThisWeek;
    }

    public void setBmi(Double bmi) {
        this.bmi = bmi;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeightHistory(List<Double> weightHistory) {
        this.weightHistory = weightHistory;
    }
}
