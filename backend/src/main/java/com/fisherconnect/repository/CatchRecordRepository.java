package com.fisherconnect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fisherconnect.entity.CatchRecord;

public interface CatchRecordRepository extends JpaRepository<CatchRecord, Long> {
    List<CatchRecord> findByUserId(Long userId);
    List<CatchRecord> findByTripId(Long tripId);
}