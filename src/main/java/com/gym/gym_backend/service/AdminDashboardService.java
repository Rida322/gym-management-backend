package com.gym.gym_backend.service;

import com.gym.gym_backend.repo.PaymentRepository;
import com.gym.gym_backend.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AdminDashboardService {

    private final UserRepository userRepo;
    private final PaymentRepository paymentRepo;

    public AdminDashboardService(UserRepository userRepo,
                                 PaymentRepository paymentRepo) {
        this.userRepo = userRepo;
        this.paymentRepo = paymentRepo;
    }

    public long totalMembers() {
        return userRepo.countByRole("MEMBER");
    }

    public long activeMembers() {
        LocalDate today = LocalDate.now();
        return paymentRepo.countActiveMembers(today);
    }

    public long expiringSoon() {
        LocalDate today = LocalDate.now();
        LocalDate soon = today.plusDays(7);
        return paymentRepo.countExpiringSoon(today, soon);
    }

    public double monthlyRevenue() {
        LocalDate start = LocalDate.now().withDayOfMonth(1);
        LocalDate end = start.plusMonths(1).minusDays(1);
        return paymentRepo.sumPaymentsByMonthYear(start, end);
    }

    public long newThisMonth() {
        LocalDate start = LocalDate.now().withDayOfMonth(1);
        LocalDate end = start.plusMonths(1).minusDays(1);
        return userRepo.countJoinedBetween(start, end);
    }
}
