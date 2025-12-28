package com.gym.gym_backend.dto;

import java.time.LocalDate;

public class SubscriptionResponse {

    private String name;
    private String phone;
    private double amount;
    private String method;
    private LocalDate startDate;
    private LocalDate endDate;
    private long daysLeft;

    public SubscriptionResponse(
            String name,
            String phone,
            double amount,
            String method,
            LocalDate startDate,
            LocalDate endDate,
            long daysLeft
    ) {
        this.name = name;
        this.phone = phone;
        this.amount = amount;
        this.method = method;
        this.startDate = startDate;
        this.endDate = endDate;
        this.daysLeft = daysLeft;
    }

    // ===== GETTERS ONLY =====
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public double getAmount() { return amount; }
    public String getMethod() { return method; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public long getDaysLeft() { return daysLeft; }
}
