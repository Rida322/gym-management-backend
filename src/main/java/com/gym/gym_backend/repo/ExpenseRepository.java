package com.gym.gym_backend.repo;

import com.gym.gym_backend.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;





public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // ================= FILTER BY MONTH / YEAR =================
    @Query("""
        SELECT e FROM Expense e
        WHERE EXTRACT(MONTH FROM e.expenseDate) = :month
          AND EXTRACT(YEAR FROM e.expenseDate) = :year
        ORDER BY e.expenseDate DESC
    """)
    List<Expense> findByMonthAndYear(@Param("month") int month,
                                     @Param("year") int year);

    // ================= SUM EXPENSES =================
    @Query("""
        SELECT COALESCE(SUM(e.cost), 0)
        FROM Expense e
        WHERE EXTRACT(MONTH FROM e.expenseDate) = :month
          AND EXTRACT(YEAR FROM e.expenseDate) = :year
    """)
    Double sumExpensesByMonthYear(@Param("month") int month,
                                  @Param("year") int year);

    // ================= MONTHLY CHART =================
    @Query("""
        SELECT EXTRACT(MONTH FROM e.expenseDate), COALESCE(SUM(e.cost),0)
        FROM Expense e
        WHERE EXTRACT(YEAR FROM e.expenseDate) = :year
        GROUP BY EXTRACT(MONTH FROM e.expenseDate)
        ORDER BY EXTRACT(MONTH FROM e.expenseDate)
    """)
    List<Object[]> monthlyExpenses(@Param("year") int year);
}


