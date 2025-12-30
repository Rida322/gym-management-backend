package com.gym.gym_backend.repo;

import com.gym.gym_backend.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;





 public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query(value = """
        SELECT * FROM expenses
        WHERE expense_date IS NOT NULL
          AND EXTRACT(MONTH FROM expense_date) = :month
          AND EXTRACT(YEAR FROM expense_date) = :year
        ORDER BY expense_date DESC
    """, nativeQuery = true)
    List<Expense> findByMonthAndYear(@Param("month") int month,
                                     @Param("year") int year);


    @Query(value = """
        SELECT COALESCE(SUM(cost), 0)
        FROM expenses
        WHERE expense_date IS NOT NULL
          AND EXTRACT(MONTH FROM expense_date) = :month
          AND EXTRACT(YEAR FROM expense_date) = :year
    """, nativeQuery = true)
    Double sumExpensesByMonthYear(@Param("month") int month,
                                  @Param("year") int year);


    @Query(value = """
        SELECT EXTRACT(MONTH FROM expense_date) AS m, COALESCE(SUM(cost),0) AS total
        FROM expenses
        WHERE expense_date IS NOT NULL
          AND EXTRACT(YEAR FROM expense_date) = :year
        GROUP BY m
        ORDER BY m
    """, nativeQuery = true)
    List<Object[]> monthlyExpenses(@Param("year") int year);
}



