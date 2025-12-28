package com.gym.gym_backend.controller;

import com.gym.gym_backend.dto.ScheduleRequest;
import com.gym.gym_backend.entity.GymSchedule;
import com.gym.gym_backend.repo.GymScheduleRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping("/api/member/schedule")
@CrossOrigin(origins = "*")
public class MemberScheduleController {

    private final GymScheduleRepository repo;

    public MemberScheduleController(GymScheduleRepository repo) {
        this.repo = repo;
    }

    // ================= SAVE =================
    @PostMapping
    public void save(@RequestBody ScheduleRequest request) {

        LocalDate today = LocalDate.now();

        // 🔒 ONE SCHEDULE PER DAY
        repo.findByUserIdAndVisitDate(request.userId, today)
                .ifPresent(repo::delete);

        GymSchedule g = new GymSchedule();
        g.setUserId(request.userId);
        g.setVisitDate(today);
        g.setVisitTime(LocalTime.parse(request.visitTime));
        g.setCreatedAt(LocalDateTime.now());

        repo.save(g);
    }

    // ================= DELETE (FINAL FIX) =================
    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Integer userId) {

        // 🔥 DELETE BY USER + TODAY (ALWAYS WORKS)
        repo.deleteByUserIdAndVisitDate(userId, LocalDate.now());
    }
}
