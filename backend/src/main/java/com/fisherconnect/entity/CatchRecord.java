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
@Table(name = "catch_records")
public class CatchRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String species;
    private Double quantityKg;
    private Double sellingPricePerKg;
    private Double totalRevenue;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private FishingTrip trip;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public CatchRecord() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }
    public Double getQuantityKg() { return quantityKg; }
    public void setQuantityKg(Double quantityKg) { this.quantityKg = quantityKg; }
    public Double getSellingPricePerKg() { return sellingPricePerKg; }
    public void setSellingPricePerKg(Double sellingPricePerKg) { this.sellingPricePerKg = sellingPricePerKg; }
    public Double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(Double totalRevenue) { this.totalRevenue = totalRevenue; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public FishingTrip getTrip() { return trip; }
    public void setTrip(FishingTrip trip) { this.trip = trip; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}