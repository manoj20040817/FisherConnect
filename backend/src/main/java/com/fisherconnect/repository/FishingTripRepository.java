package com.fisherconnect.repository;

import com.fisherconnect.entity.FishingTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FishingTripRepository extends JpaRepository<FishingTrip, Long> {
    List<FishingTrip> findByUserId(Long userId);
    Optional<FishingTrip> findByUserIdAndId(Long userId, Long id);
    List<FishingTrip> findByUserIdAndStatus(Long userId, String status);
}
