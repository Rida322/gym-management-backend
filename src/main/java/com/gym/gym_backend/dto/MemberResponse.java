package com.gym.gym_backend.dto;

public class MemberResponse {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String status;
    private boolean expiringSoon;

    public MemberResponse(Long id, String name, String email,
                          String phoneNumber, String status,boolean expiringSoon) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.expiringSoon = expiringSoon;
    }

    // ===== Getters =====
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getStatus() { return status; }
    public boolean isExpiringSoon() {
        return expiringSoon;
    }
}
