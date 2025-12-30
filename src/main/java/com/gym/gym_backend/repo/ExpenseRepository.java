package com.gym.gym_backend.repo;

import com.gym.gym_backend.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("""
        SELECT e FROM Expense e
        WHERE EXTRACT(MONTH FROM e.createdAt) = :month
          AND EXTRACT(YEAR FROM e.createdAt) = :year
        ORDER BY e.createdAt DESC
    """)
    List<Expense> findByMonthAndYear(@Param("month") int month, @Param("year") int year);

    @Query("""
        SELECT COALESCE(SUM(e.cost), 0)
        FROM Expense e
        WHERE EXTRACT(MONTH FROM e.createdAt) = :month
          AND EXTRACT(YEAR FROM e.createdAt) = :year
    """)
    double sumExpensesByMonthYear(@Param("month") int month, @Param("year") int year);

    @Query("""
        SELECT EXTRACT(MONTH FROM e.createdAt), COALESCE(SUM(e.cost),0)
        FROM Expense e
        WHERE EXTRACT(YEAR FROM e.createdAt) = :year
        GROUP BY EXTRACT(MONTH FROM e.createdAt)
        ORDER BY EXTRACT(MONTH FROM e.createdAt)
    """)
    List<Object[]> monthlyExpenses(@Param("year") int year);
}

