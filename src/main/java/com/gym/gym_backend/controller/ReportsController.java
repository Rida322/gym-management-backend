package com.gym.gym_backend.controller;

import com.gym.gym_backend.dto.MonthlyPoint;
import com.gym.gym_backend.dto.ReportsCharts;
import com.gym.gym_backend.dto.ReportsSummary;
import com.gym.gym_backend.repo.ExpenseRepository;
import com.gym.gym_backend.repo.PaymentRepository;
import com.gym.gym_backend.repo.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/admin/reports")
@CrossOrigin(origins = "*")
public class ReportsController {

    private final PaymentRepository paymentRepo;
    private final ExpenseRepository expenseRepo;
    private final UserRepository userRepo;

    // ===== Constructor Injection =====
    public ReportsController(
            PaymentRepository paymentRepo,
            ExpenseRepository expenseRepo,
            UserRepository userRepo
    ) {
        this.paymentRepo = paymentRepo;
        this.expenseRepo = expenseRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/summary")
    public ReportsSummary summary(@RequestParam int month,
                                  @RequestParam int year) {

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.plusMonths(1).minusDays(1);

        double totalPayments = paymentRepo.sumPaymentsByMonthYear(start, end);
        double totalExpenses = expenseRepo.sumExpensesByMonthYear(start, end);
        double netProfit = totalPayments - totalExpenses;
        int newMembers = (int) userRepo.countJoinedBetween(start, end);

        return new ReportsSummary(totalPayments, totalExpenses, netProfit, newMembers);
    }





    // ================= CHARTS =================
    @GetMapping("/charts")
    public ReportsCharts charts(@RequestParam int year) {

        Map<Integer, Double> expensesMap =
                toMap(expenseRepo.monthlyExpenses(year));

        Map<Integer, Double> paymentsMap =
                toMap(paymentRepo.monthlyPayments(year));

        List<MonthlyPoint> monthlyExpenses = new ArrayList<>();
        List<MonthlyPoint> monthlyProfit = new ArrayList<>();

        for (int m = 1; m <= 12; m++) {
            double expenses = expensesMap.getOrDefault(m, 0.0);
            double payments = paymentsMap.getOrDefault(m, 0.0);

            monthlyExpenses.add(new MonthlyPoint(m, expenses));
            monthlyProfit.add(new MonthlyPoint(m, payments - expenses));
        }

        return new ReportsCharts(monthlyExpenses, monthlyProfit);
    }

    // ================= HELPER =================
    private Map<Integer, Double> toMap(List<Object[]> rows) {
        Map<Integer, Double> map = new HashMap<>();
        for (Object[] r : rows) {
            map.put(
                    ((Number) r[0]).intValue(),
                    ((Number) r[1]).doubleValue()
            );
        }
        return map;
    }
}
