package com.gym.gym_backend.dto;

import java.util.List;

public record ReportsCharts(
        List<MonthlyPoint> monthlyExpenses,
        List<MonthlyPoint> monthlyProfit
) {}
