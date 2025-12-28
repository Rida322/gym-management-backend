package com.gym.gym_backend.controller;

import com.gym.gym_backend.dto.PaymentReportDto;
import com.gym.gym_backend.dto.PaymentResponse;
import com.gym.gym_backend.entity.Payment;
import com.gym.gym_backend.entity.Expense;
import com.gym.gym_backend.entity.User;
import com.gym.gym_backend.repo.PaymentRepository;
import com.gym.gym_backend.repo.ExpenseRepository;
import com.gym.gym_backend.repo.UserRepository;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentRepository paymentRepo;
    private final ExpenseRepository expenseRepo;
    private final UserRepository userRepository;

    public PaymentController(PaymentRepository paymentRepo,
                             ExpenseRepository expenseRepo,
                             UserRepository userRepository) {
        this.paymentRepo = paymentRepo;
        this.expenseRepo = expenseRepo;
        this.userRepository = userRepository;
    }

    // ================= PAYMENTS =================

    @GetMapping
    public List<PaymentResponse> getAllPayments() {
        return paymentRepo.findAll().stream().map(p -> {

            String name = "-";

            Optional<User> userOpt = userRepository.findByEmail(p.getEmail());
            if (userOpt.isPresent()) {
                name = userOpt.get().getName();
            }

            return new PaymentResponse(
                    p.getId(),                 // ✅ payment id
                    name,                      // ✅ resolved name
                    p.getEmail(),              // ✅ email
                    p.getPhoneNumber(),        // ✅ phone
                    p.getAmount(),             // ✅ amount
                    p.getStartDate(),           // ✅ start
                    p.getEndDate()              // ✅ end
            );

        }).toList();
    }





    @PostMapping
    public Payment recordPayment(@RequestBody Payment payment) {

        // Default start date
        if (payment.getStartDate() == null) {
            payment.setStartDate(LocalDate.now());
        }

        // Default end date (example: +1 day)
        if (payment.getEndDate() == null) {
            payment.setEndDate(LocalDate.now().plusDays(1));
        }

        // Default payment method
        if (payment.getPaymentMethod() == null) {
            payment.setPaymentMethod("Cash");
        }

        return paymentRepo.save(payment);
    }


    // ================= EXPENSES =================

    @GetMapping("/expenses")
    public List<Expense> getAllExpenses() {
        return expenseRepo.findAll();
    }

    @GetMapping("/expenses/filter")
    public List<Expense> getExpensesByMonth(
            @RequestParam int month,
            @RequestParam int year
    ) {
        return expenseRepo.findByMonthAndYear(month, year);
    }

    @PostMapping("/expenses")
    public Expense addExpense(@RequestBody Expense expense) {
        if (expense.getExpenseDate() == null) {
            expense.setExpenseDate(LocalDate.now());
        }
        return expenseRepo.save(expense);
    }
    @GetMapping("/report")
    public List<PaymentReportDto> getPaymentReport() {
        return paymentRepo.getPaymentReport();
    }
    @GetMapping("/member/{phone}")
    public User findMemberByPhone(@PathVariable String phone) {

        return userRepository.findByPhoneNumber(phone)
                .orElseThrow(() ->
                        new RuntimeException("Member not found"));
    }
    // ================= DELETE PAYMENT =================
    @DeleteMapping("/{id}")
    public void deletePayment(@PathVariable Long id) {
        paymentRepo.deleteById(id);
    }

    // ================= DELETE EXPENSE =================
    @DeleteMapping("/expenses/{id}")
    public void deleteExpense(@PathVariable Long id) {
        expenseRepo.deleteById(id);
    }



}
