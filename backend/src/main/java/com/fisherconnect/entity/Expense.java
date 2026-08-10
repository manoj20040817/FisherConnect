package com.fisherconnect.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category; // FUEL, ICE, FOOD, MAINTENANCE, OTHER
    private Double amount;
    private String description;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private FishingTrip trip;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Expense() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public FishingTrip getTrip() { return trip; }
    public void setTrip(FishingTrip trip) { this.trip = trip; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}