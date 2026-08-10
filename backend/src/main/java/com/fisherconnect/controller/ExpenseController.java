package com.fisherconnect.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fisherconnect.entity.Expense;
import com.fisherconnect.entity.User;
import com.fisherconnect.repository.ExpenseRepository;
import com.fisherconnect.repository.UserRepository;

@RestController
@RequestMapping("/api/fisherman/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepo;

    @Autowired
    private UserRepository userRepo;

    @GetMapping
    public ResponseEntity<List<Expense>> getMyExpenses(Principal principal) {
        User user = userRepo.findByEmail(principal.getName()).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(expenseRepo.findByUserId(user.getId()));
    }

    @PostMapping
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense, Principal principal) {
        User user = userRepo.findByEmail(principal.getName()).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();
        expense.setUser(user);
        expense.setCreatedAt(LocalDateTime.now());
        return ResponseEntity.ok(expenseRepo.save(expense));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long id, Principal principal) {
        User user = userRepo.findByEmail(principal.getName()).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();
        if (expenseRepo.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        expenseRepo.deleteById(id);
        return ResponseEntity.ok("Expense deleted");
    }
}