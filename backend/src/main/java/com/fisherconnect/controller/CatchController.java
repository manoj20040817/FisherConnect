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

import com.fisherconnect.entity.CatchRecord;
import com.fisherconnect.entity.User;
import com.fisherconnect.repository.CatchRecordRepository;
import com.fisherconnect.repository.UserRepository;

@RestController
@RequestMapping("/api/fisherman/catches")
public class CatchController {

    @Autowired
    private CatchRecordRepository catchRepo;

    @Autowired
    private UserRepository userRepo;

    @GetMapping
    public ResponseEntity<List<CatchRecord>> getMyCatches(Principal principal) {
        User user = userRepo.findByEmail(principal.getName()).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(catchRepo.findByUserId(user.getId()));
    }

    @PostMapping
    public ResponseEntity<CatchRecord> addCatch(@RequestBody CatchRecord record, Principal principal) {
        User user = userRepo.findByEmail(principal.getName()).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();
        record.setUser(user);
        record.setCreatedAt(LocalDateTime.now());
        if (record.getTotalRevenue() == null && record.getQuantityKg() != null && record.getSellingPricePerKg() != null) {
            record.setTotalRevenue(record.getQuantityKg() * record.getSellingPricePerKg());
        }
        return ResponseEntity.ok(catchRepo.save(record));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCatch(@PathVariable Long id, Principal principal) {
        User user = userRepo.findByEmail(principal.getName()).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();
        if (catchRepo.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        catchRepo.deleteById(id);
        return ResponseEntity.ok("Catch deleted");
    }
}