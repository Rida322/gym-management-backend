package com.gym.gym_backend.controller;

import com.gym.gym_backend.dto.MemberDashboardDTO;
import com.gym.gym_backend.service.MemberDashboardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member/dashboard")
public class MemberDashboardController {

    private final MemberDashboardService service;

    public MemberDashboardController(MemberDashboardService service) {
        this.service = service;
    }

    @GetMapping("/{userId}")
    public MemberDashboardDTO dashboard(@PathVariable Integer userId) {
        return service.getDashboard(userId);
    }
}
