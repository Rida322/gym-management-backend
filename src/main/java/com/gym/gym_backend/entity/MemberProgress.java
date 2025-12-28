package com.gym.gym_backend.entity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "member_progress")
public class MemberProgress {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    private Double weight;

    private LocalDate progressDate;

    public Integer getUserId() {
        return userId;
    }

    public Integer getId() {
        return id;
    }

    public Double getWeight() {
        return weight;
    }

    public LocalDate getProgressDate() {
        return progressDate;
    }
}
