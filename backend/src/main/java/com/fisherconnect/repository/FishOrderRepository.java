package com.fisherconnect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fisherconnect.entity.FishOrder;

public interface FishOrderRepository extends JpaRepository<FishOrder, Long> {
    List<FishOrder> findByBuyerId(Long buyerId);
    List<FishOrder> findBySellerId(Long sellerId);
}