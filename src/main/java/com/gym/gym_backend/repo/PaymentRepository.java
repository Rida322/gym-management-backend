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


    @Query(value = """
    SELECT COUNT(DISTINCT email)
    FROM payments
    WHERE CURRENT_DATE BETWEEN start_date AND end_date
""", nativeQuery = true)
    long countActiveMembers();



    @Query(value = """
    SELECT COUNT(DISTINCT email)
    FROM payments
    WHERE end_date BETWEEN CURRENT_DATE
    AND CURRENT_DATE + INTERVAL '7 days'
""", nativeQuery = true)
    long countExpiringSoon();

    @Query(value = """
    SELECT COALESCE(SUM(amount),0)
    FROM payments
    WHERE start_date BETWEEN
          DATE_TRUNC('month', CURRENT_DATE)::DATE
      AND (DATE_TRUNC('month', CURRENT_DATE) + INTERVAL '1 month - 1 day')::DATE
""", nativeQuery = true)
    double sumThisMonth();


    // ================= REPORTS =================

    @Query("""
        SELECT COALESCE(SUM(p.amount),0)
        FROM Payment p
        WHERE p.startDate BETWEEN :start AND :end
    """)
    double sumPaymentsByMonthYear(@Param("start") LocalDate start,
                                  @Param("end") LocalDate end);

    // 📊 Monthly Payments Chart
    @Query(value = """
        SELECT EXTRACT(MONTH FROM start_date) AS m, COALESCE(SUM(amount),0) AS total
        FROM payments
        WHERE EXTRACT(YEAR FROM start_date) = :year
        GROUP BY m
        ORDER BY m
    """, nativeQuery = true)
    List<Object[]> monthlyPayments(int year);




    // ================= PAYMENTS PAGE =================

    @Query(value = """
    SELECT *
    FROM payments
    WHERE EXTRACT(MONTH FROM start_date) = :month
      AND EXTRACT(YEAR FROM start_date) = :year
""", nativeQuery = true)
    List<Payment> findByMonthAndYear(@Param("month") int month,
                                     @Param("year") int year);


    @Query("""
        SELECT new com.gym.gym_backend.dto.PaymentReportDto(
            u.name, p.email, p.phoneNumber, p.amount, p.paymentMethod, p.startDate, p.endDate
        )
        FROM Payment p JOIN User u ON u.email = p.email
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
