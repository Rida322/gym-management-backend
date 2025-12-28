package com.gym.gym_backend.controller;

import com.gym.gym_backend.dto.SubscriptionResponse;
import com.gym.gym_backend.entity.Payment;
import com.gym.gym_backend.repo.PaymentRepository;
import com.gym.gym_backend.repo.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/api/admin/subscriptions")
@CrossOrigin(origins = "*")
public class SubscriptionController {

    private final PaymentRepository paymentRepo;
    private final UserRepository userRepo;

    public SubscriptionController(PaymentRepository paymentRepo,
                                  UserRepository userRepo) {
        this.paymentRepo = paymentRepo;
        this.userRepo = userRepo;
    }

    // ================= EXPIRED =================
    @GetMapping("/expired")
    public List<SubscriptionResponse> expired() {

        LocalDate today = LocalDate.now();

        return paymentRepo.findExpired(today)
                .stream()
                .map(this::map)
                .toList();
    }

    // ================= EXPIRING SOON =================
    @GetMapping("/expiring-soon")
    public List<SubscriptionResponse> expiringSoon() {

        LocalDate today = LocalDate.now();
        LocalDate limit = today.plusDays(7);

        return paymentRepo.findExpiringSoon(today, limit)
                .stream()
                .map(this::map)
                .toList();
    }

    // ================= MAPPER =================
    private SubscriptionResponse map(Payment p) {

        AtomicReference<String> name = new AtomicReference<>("-");
        userRepo.findByEmail(p.getEmail())
                .ifPresent(u -> name.set(u.getName()));

        long daysLeft =
                ChronoUnit.DAYS.between(LocalDate.now(), p.getEndDate());

        return new SubscriptionResponse(
                name.get(),
                p.getPhoneNumber(),
                p.getAmount(),
                p.getPaymentMethod(),
                p.getStartDate(),
                p.getEndDate(),
                daysLeft
        );
    }
}

