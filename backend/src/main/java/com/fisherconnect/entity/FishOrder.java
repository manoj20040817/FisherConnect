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
@Table(name = "fish_orders")
public class FishOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double quantityKg;
    private Double totalPrice;
    private String status; // PENDING, CONFIRMED, COMPLETED, CANCELLED
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "listing_id")
    private FishListing listing;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    public FishOrder() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Double getQuantityKg() { return quantityKg; }
    public void setQuantityKg(Double quantityKg) { this.quantityKg = quantityKg; }
    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public FishListing getListing() { return listing; }
    public void setListing(FishListing listing) { this.listing = listing; }
    public User getBuyer() { return buyer; }
    public void setBuyer(User buyer) { this.buyer = buyer; }
    public User getSeller() { return seller; }
    public void setSeller(User seller) { this.seller = seller; }
}