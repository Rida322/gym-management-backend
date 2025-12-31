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
        return paymentRepo.countActiveMembers();
    }

    public long expiringSoon() {
        return paymentRepo.countExpiringSoon();
    }

    public double monthlyRevenue() {
        return paymentRepo.sumThisMonth();
    }

    public long newThisMonth() {
        return userRepo.countJoinedThisMonth();
    }
}

