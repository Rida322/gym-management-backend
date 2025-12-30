package com.gym.gym_backend.repo;

import com.gym.gym_backend.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;





public interface ExpenseRepository extends JpaRepository<Expense, Long> {

 @Query("""
        SELECT e FROM Expense e
        WHERE EXTRACT(MONTH FROM e.expenseDate) = :month
          AND EXTRACT(YEAR FROM e.expenseDate) = :year
        ORDER BY e.expenseDate DESC
    """)
 List<Expense> findByMonthAndYear(@Param("month") int month,
                                  @Param("year") int year);

 @Query("""
        SELECT COALESCE(SUM(e.cost),0)
        FROM Expense e
        WHERE e.expenseDate BETWEEN :start AND :end
    """)
 double sumExpensesByMonthYear(@Param("start") LocalDate start,
                               @Param("end") LocalDate end);

 @Query("""
        SELECT EXTRACT(MONTH FROM e.expenseDate), COALESCE(SUM(e.cost),0)
        FROM Expense e
        WHERE EXTRACT(YEAR FROM e.expenseDate) = :year
        GROUP BY EXTRACT(MONTH FROM e.expenseDate)
        ORDER BY 1
    """)
 List<Object[]> monthlyExpenses(@Param("year") int year);
}




