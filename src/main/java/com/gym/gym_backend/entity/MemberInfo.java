package com.gym.gym_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "member_info")
public class MemberInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    private Double weight;
    private Integer height;
    private String experience;

    // ✅ GETTERS (REQUIRED)
    public Double getWeight() {
        return weight;
    }

    public Integer getHeight() {
        return height;
    }

    public String getExperience() {
        return experience;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
}
