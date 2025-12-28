package com.gym.gym_backend.repo;

import com.gym.gym_backend.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // Filter table (month/year)
    @Query(value = """
        SELECT * FROM expenses
        WHERE expense_date IS NOT NULL
          AND MONTH(expense_date) = :month
          AND YEAR(expense_date) = :year
        ORDER BY expense_date DESC
    """, nativeQuery = true)
    List<Expense> findByMonthAndYear(@Param("month") int month, @Param("year") int year);

    // Total expenses (month/year)
    @Query(value = """
        SELECT COALESCE(SUM(cost), 0)
        FROM expenses
        WHERE expense_date IS NOT NULL
          AND MONTH(expense_date) = :month
          AND YEAR(expense_date) = :year
    """, nativeQuery = true)
    double sumExpensesByMonthYear(@Param("month") int month, @Param("year") int year);

    // Monthly expenses chart (returns month number + sum)
    @Query(value = """
        SELECT MONTH(expense_date) AS m, COALESCE(SUM(cost),0) AS total
        FROM expenses
        WHERE expense_date IS NOT NULL
          AND YEAR(expense_date) = :year
        GROUP BY MONTH(expense_date)
        ORDER BY m
    """, nativeQuery = true)
    List<Object[]> monthlyExpenses(@Param("year") int year);
}
