package com.gym.gym_backend.dto;

public record ReportsSummary(
        double totalPayments,
        double totalExpenses,
        double netProfit,
        int newMembers
) {}
