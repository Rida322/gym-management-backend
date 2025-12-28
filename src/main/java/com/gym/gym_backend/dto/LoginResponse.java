package com.gym.gym_backend.dto;

public class LoginResponse {

    public boolean success;
    public String message;
    public Integer userId;
    public String email;
    public String role;

    public static LoginResponse ok(Integer userId, String email, String role) {
        LoginResponse r = new LoginResponse();
        r.success = true;
        r.message = "Login successful";
        r.userId = userId;
        r.email = email;
        r.role = role;
        return r;
    }

    public static LoginResponse fail(String msg) {
        LoginResponse r = new LoginResponse();
        r.success = false;
        r.message = msg;
        return r;
    }
}

