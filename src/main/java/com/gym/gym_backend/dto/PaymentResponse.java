package com.gym.gym_backend.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentResponse {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private double amount;
    private LocalDate startDate;
    private LocalDate endDate;

    public PaymentResponse(
            Long id,
            String name,
            String email,
            String phoneNumber,
            double amount,
            LocalDate startDate,
            LocalDate endDate
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
}



