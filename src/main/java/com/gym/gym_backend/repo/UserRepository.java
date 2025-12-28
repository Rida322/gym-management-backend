package com.gym.gym_backend.repo;

import com.gym.gym_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    // ================= BASIC =================
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);

    // ================= COUNTING =================

    // 🔹 Count members by role (USED BY DASHBOARD)
    long countByRole(String role);

    // 🔹 Total members (explicit query – optional)
    @Query("""
        SELECT COUNT(u)
        FROM User u
        WHERE u.role = 'MEMBER'
    """)
    long countMembers();

    // 🔹 New members this month
    @Query("""
        SELECT COUNT(u)
        FROM User u
        WHERE u.role = 'MEMBER'
          AND MONTH(u.createdAt) = MONTH(CURRENT_DATE)
          AND YEAR(u.createdAt) = YEAR(CURRENT_DATE)
    """)
    long countJoinedThisMonth();

    // ================= MEMBERS + STATUS =================
    // Subscription status is derived from PAYMENTS (NO subscription table)
    @Query(value = """
        SELECT 
            u.id,
            u.name,
            u.email,
            u.phone_number,
            p.end_date
        FROM users u
        LEFT JOIN (
            SELECT email, MAX(end_date) AS end_date
            FROM payments
            GROUP BY email
        ) p ON p.email = u.email
        WHERE u.role = 'MEMBER'
    """, nativeQuery = true)
    List<Object[]> findMembersWithStatus();
}
