package com.fisherconnect.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fisherconnect.entity.Boat;

public interface BoatRepository extends JpaRepository<Boat, Long> {
    List<Boat> findByUserId(Long userId);
    Optional<Boat> findByUserIdAndId(Long userId, Long id);
}