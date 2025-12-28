package com.gym.gym_backend.dto;

public class StatsResponse {

    private long totalMembers;
    private long activeMembers;
    private long expiringSoon;
    private double monthlyRevenue;
    private long newThisMonth;

    public StatsResponse(
            long totalMembers,
            long activeMembers,
            long expiringSoon,
            double monthlyRevenue,
            long newThisMonth
    ) {
        this.totalMembers = totalMembers;
        this.activeMembers = activeMembers;
        this.expiringSoon = expiringSoon;
        this.monthlyRevenue = monthlyRevenue;
        this.newThisMonth = newThisMonth;
    }

    public long getTotalMembers() { return totalMembers; }
    public long getActiveMembers() { return activeMembers; }
    public long getExpiringSoon() { return expiringSoon; }
    public double getMonthlyRevenue() { return monthlyRevenue; }
    public long getNewThisMonth() { return newThisMonth; }
}
