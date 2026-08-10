package com.fisherconnect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fisherconnect.entity.FishListing;

public interface FishListingRepository extends JpaRepository<FishListing, Long> {
    List<FishListing> findByStatus(String status);
    List<FishListing> findByUserId(Long userId);
}