package com.gym.gym_backend.controller;

import com.gym.gym_backend.dto.MemberResponse;
import com.gym.gym_backend.entity.User;
import com.gym.gym_backend.repo.PaymentRepository;
import com.gym.gym_backend.repo.UserRepository;
import org.springframework.web.bind.annotation.*;
import com.gym.gym_backend.dto.StatsResponse;
import java.time.LocalDate;
import java.util.List;
@RestController
@RequestMapping("/api/admin/members")
@CrossOrigin(origins = "*")
public class MembersController {

    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;

    public MembersController(UserRepository userRepository,
                             PaymentRepository paymentRepository) {
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
    }

    // ================= MEMBERS LIST =================
    @GetMapping
    public List<MemberResponse> getAllMembers() {

        LocalDate today = LocalDate.now();
        LocalDate soon = today.plusDays(7);

        return userRepository.findMembersWithStatus()
                .stream()
                .map(row -> {

                    Long id = ((Number) row[0]).longValue();
                    String name = (String) row[1];
                    String email = (String) row[2];
                    String phone = (String) row[3];
                    LocalDate endDate = row[4] == null
                            ? null
                            : ((java.sql.Date) row[4]).toLocalDate();

                    String status;
                    boolean expiringSoon = false;

                    if (endDate == null) {
                        status = "NO_SUBSCRIPTION";
                    } else if (endDate.isBefore(today)) {
                        status = "EXPIRED";
                    } else {
                        status = "ACTIVE";
                        expiringSoon = !endDate.isAfter(soon);
                    }

                    return new MemberResponse(
                            id,
                            name,
                            email,
                            phone,
                            status,
                            expiringSoon
                    );
                })
                .toList();
    }

    @GetMapping("/stats")
    public StatsResponse getStats() {

        long total = userRepository.countByRole("MEMBER");
        long active = paymentRepository.countActiveMembers();
        long expiring = paymentRepository.countExpiringSoon();
        double revenue = paymentRepository.sumThisMonth();
        long newThisMonth = userRepository.countJoinedThisMonth();

        return new StatsResponse(total, active, expiring, revenue, newThisMonth);
    }



}

