package com.gym.gym_backend.repo;

import com.gym.gym_backend.dto.PaymentReportDto;
import com.gym.gym_backend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // ================= DASHBOARD =================


    // ACTIVE MEMBERS
    @Query("""
    SELECT COUNT(DISTINCT p.email)
    FROM Payment p
    WHERE CURRENT_DATE BETWEEN p.startDate AND p.endDate
""")
    long countActiveMembers();


    // EXPIRING SOON (next 7 days)
    @Query("""
    SELECT COUNT(DISTINCT p.email)
    FROM Payment p
    WHERE p.endDate BETWEEN CURRENT_DATE AND CURRENT_DATE + 7
""")
    long countExpiringSoon();


    // MONTHLY REVENUE
    @Query("""
    SELECT COALESCE(SUM(p.amount),0)
    FROM Payment p
    WHERE p.startDate >= DATE_TRUNC('month', CURRENT_DATE)
""")
    double sumThisMonth();


    // ================= REPORTS =================

    @Query("""
        SELECT COALESCE(SUM(p.amount),0)
        FROM Payment p
        WHERE p.startDate BETWEEN :start AND :end
    """)
    double sumPaymentsByMonthYear(@Param("start") LocalDate start,
                                  @Param("end") LocalDate end);

    @Query("""
        SELECT EXTRACT(MONTH FROM p.startDate), COALESCE(SUM(p.amount),0)
        FROM Payment p
        WHERE EXTRACT(YEAR FROM p.startDate) = :year
        GROUP BY EXTRACT(MONTH FROM p.startDate)
        ORDER BY 1
    """)
    List<Object[]> monthlyPayments(@Param("year") int year);




    // ================= PAYMENTS PAGE =================

    @Query(value = """
        SELECT *
        FROM payments
        WHERE MONTH(start_date) = :month
          AND YEAR(start_date) = :year
    """, nativeQuery = true)
    List<Payment> findByMonthAndYear(
            @Param("month") int month,
            @Param("year") int year
    );

    @Query("""
        SELECT new com.gym.gym_backend.dto.PaymentReportDto(
            u.name,
            p.email,
            p.phoneNumber,
            p.amount,
            p.paymentMethod,
            p.startDate,
            p.endDate
        )
        FROM Payment p
        JOIN User u ON u.email = p.email
        ORDER BY p.startDate DESC
    """)
    List<PaymentReportDto> getPaymentReport();

    @Query("""
        SELECT p FROM Payment p
        WHERE p.endDate < :today
    """)
    List<Payment> findExpired(@Param("today") LocalDate today);

    @Query("""
        SELECT p FROM Payment p
        WHERE p.endDate BETWEEN :today AND :limitDate
    """)
    List<Payment> findExpiringSoon(
            @Param("today") LocalDate today,
            @Param("limitDate") LocalDate limitDate
    );

    // ================= REPORTS / CHARTS =================



}
