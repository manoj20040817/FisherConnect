package com.fisherconnect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fisherconnect.entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserId(Long userId);
    List<Expense> findByTripId(Long tripId);
}