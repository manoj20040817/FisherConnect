package com.fisherconnect.controller;

import com.fisherconnect.entity.*;
import com.fisherconnect.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fisherman/trips")
public class TripController {

    @Autowired
    private FishingTripRepository tripRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BoatRepository boatRepo;

    @GetMapping
    public ResponseEntity<List<FishingTrip>> getMyTrips(Principal principal) {
        User user = userRepo.findByEmail(principal.getName()).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(tripRepo.findByUserId(user.getId()));
    }

    @PostMapping
    public ResponseEntity<FishingTrip> createTrip(@RequestBody FishingTrip trip, Principal principal) {
        User user = userRepo.findByEmail(principal.getName()).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();
        List<FishingTrip> activeTrips = tripRepo.findByUserIdAndStatus(user.getId(), "ACTIVE");
        activeTrips.forEach(t -> {
            t.setStatus("COMPLETED");
            t.setEndDate(LocalDateTime.now());
            tripRepo.save(t);
        });
        trip.setUser(user);
        trip.setStatus("PLANNED");
        trip.setCreatedAt(LocalDateTime.now());
        return ResponseEntity.ok(tripRepo.save(trip));
    }

    @PutMapping("/{id}/start")
    public ResponseEntity<FishingTrip> startTrip(@PathVariable Long id, Principal principal) {
        User user = userRepo.findByEmail(principal.getName()).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();
        Optional<FishingTrip> opt = tripRepo.findByUserIdAndId(user.getId(), id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        FishingTrip trip = opt.get();
        List<FishingTrip> activeTrips = tripRepo.findByUserIdAndStatus(user.getId(), "ACTIVE");
        activeTrips.forEach(t -> {
            t.setStatus("COMPLETED");
            t.setEndDate(LocalDateTime.now());
            tripRepo.save(t);
        });
        trip.setStatus("ACTIVE");
        trip.setStartDate(LocalDateTime.now());
        return ResponseEntity.ok(tripRepo.save(trip));
    }

    @PutMapping("/{id}/end")
    public ResponseEntity<FishingTrip> endTrip(@PathVariable Long id, Principal principal) {
        User user = userRepo.findByEmail(principal.getName()).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();
        Optional<FishingTrip> opt = tripRepo.findByUserIdAndId(user.getId(), id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        FishingTrip trip = opt.get();
        trip.setStatus("COMPLETED");
        trip.setEndDate(LocalDateTime.now());
        return ResponseEntity.ok(tripRepo.save(trip));
    }
}
