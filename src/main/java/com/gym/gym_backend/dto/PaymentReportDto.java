package com.gym.gym_backend.dto;

import java.time.LocalDate;

public class PaymentReportDto {

    private String name;
    private String email;
    private String phoneNumber;
    private double amount;
    private String paymentMethod;
    private LocalDate startDate;
    private LocalDate endDate;

    public PaymentReportDto(
            String name,
            String email,
            String phoneNumber,
            double amount,
            String paymentMethod,
            LocalDate startDate,
            LocalDate endDate
    ) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // getters only
}
