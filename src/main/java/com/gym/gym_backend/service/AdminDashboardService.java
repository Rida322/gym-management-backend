package com.gym.gym_backend.service;

import com.gym.gym_backend.repo.PaymentRepository;
import com.gym.gym_backend.repo.SubscriptionRepository;
import com.gym.gym_backend.repo.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminDashboardService {

    private final UserRepository userRepo;
    private final SubscriptionRepository subscriptionRepo;
    private final PaymentRepository paymentRepo;

    public AdminDashboardService(
            UserRepository userRepo,
            SubscriptionRepository subscriptionRepo,
            PaymentRepository paymentRepo
    ) {
        this.userRepo = userRepo;
        this.subscriptionRepo = subscriptionRepo;
        this.paymentRepo = paymentRepo;
    }

    public long totalMembers() {
        return userRepo.countMembers(); // ✅ MEMBERS only
    }

    public long activeMembers() {
        return subscriptionRepo.countActive();
    }

    public long expiringSoon() {
        return subscriptionRepo.countExpiringSoon();
    }

    public double monthlyRevenue() {
        return paymentRepo.sumThisMonth();
    }
}
