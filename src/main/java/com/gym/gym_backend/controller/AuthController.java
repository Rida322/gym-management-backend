package com.gym.gym_backend.controller;

import com.gym.gym_backend.dto.LoginRequest;
import com.gym.gym_backend.dto.LoginResponse;
import com.gym.gym_backend.dto.SignUpRequest;
import com.gym.gym_backend.entity.MemberInfo;
import com.gym.gym_backend.entity.User;
import com.gym.gym_backend.repo.MemberInfoRepository;
import com.gym.gym_backend.repo.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // OK for development
public class AuthController {

    private final UserRepository userRepository;
    private final MemberInfoRepository memberInfoRepository;

    public AuthController(
            UserRepository userRepository,
            MemberInfoRepository memberInfoRepository
    ) {
        this.userRepository = userRepository;
        this.memberInfoRepository = memberInfoRepository;
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        User user = userRepository.findByEmail(request.email).orElse(null);

        if (user == null) {
            return LoginResponse.fail("User not found");
        }

        if (!user.getPassword().equals(request.password)) {
            return LoginResponse.fail("Wrong password");
        }

        return LoginResponse.ok(
                user.getId(),
                user.getEmail(),
                user.getRole()
        );
    }

    // ================= SIGNUP =================
    @PostMapping("/signup")
    public LoginResponse signup(@RequestBody SignUpRequest request) {

        if (request.email == null || request.password == null ||
                request.name == null || request.phoneNumber == null) {
            return LoginResponse.fail("All fields are required");
        }

        if (userRepository.existsByEmail(request.email)) {
            return LoginResponse.fail("Email already exists");
        }

        // 1️⃣ Create user
        User user = new User();
        user.setEmail(request.email);
        user.setPassword(request.password); // plain text for now
        user.setName(request.name);
        user.setPhoneNumber(request.phoneNumber);
        user.setRole("MEMBER");

        User savedUser = userRepository.save(user);

        // 2️⃣ Create default member_info (CRITICAL FIX)
        MemberInfo info = new MemberInfo();
        info.setUserId(savedUser.getId());
        info.setWeight(0.0);
        info.setHeight(0);
        info.setExperience("Beginner");

        memberInfoRepository.save(info);

        return LoginResponse.ok(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }
}
