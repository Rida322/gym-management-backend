package com.gym.gym_backend.repo;

import com.gym.gym_backend.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
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


     // 💸 Sum expenses by month/year
     @Query("""
    SELECT COALESCE(SUM(e.cost),0)
    FROM Expense e
    WHERE e.expenseDate BETWEEN :start AND :end
""")
     double sumExpensesByMonthYear(@Param("start") LocalDate start,
                                   @Param("end") LocalDate end);


     // 📊 Monthly expenses chart
     @Query("""
    SELECT EXTRACT(MONTH FROM e.expenseDate), COALESCE(SUM(e.cost),0)
    FROM Expense e
    WHERE EXTRACT(YEAR FROM e.expenseDate) = :year
    GROUP BY EXTRACT(MONTH FROM e.expenseDate)
    ORDER BY 1
""")
     List<Object[]> monthlyExpenses(@Param("year") int year);
}



