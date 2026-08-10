package com.fisherconnect.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fisherconnect.entity.Boat;
import com.fisherconnect.entity.User;
import com.fisherconnect.repository.BoatRepository;
import com.fisherconnect.repository.UserRepository;

@RestController
@RequestMapping("/api/fisherman/boats")
public class BoatController {

    @Autowired
    private BoatRepository boatRepo;

    @Autowired
    private UserRepository userRepo;

    @GetMapping
    public ResponseEntity<List<Boat>> getMyBoats(Principal principal) {
        User user = userRepo.findByEmail(principal.getName()).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(boatRepo.findByUserId(user.getId()));
    }

    @PostMapping
    public ResponseEntity<Boat> addBoat(@RequestBody Boat boat, Principal principal) {
        User user = userRepo.findByEmail(principal.getName()).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();
        boat.setUser(user);
        boat.setCreatedAt(LocalDateTime.now().toString());
        boat.setStatus("ACTIVE");
        return ResponseEntity.ok(boatRepo.save(boat));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boat> updateBoat(@PathVariable Long id, @RequestBody Boat updated, Principal principal) {
        User user = userRepo.findByEmail(principal.getName()).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();
        Optional<Boat> opt = boatRepo.findByUserIdAndId(user.getId(), id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        Boat boat = opt.get();
        boat.setName(updated.getName());
        boat.setRegNumber(updated.getRegNumber());
        boat.setType(updated.getType());
        boat.setLength(updated.getLength());
        boat.setCapacity(updated.getCapacity());
        return ResponseEntity.ok(boatRepo.save(boat));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBoat(@PathVariable Long id, Principal principal) {
        User user = userRepo.findByEmail(principal.getName()).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();
        Optional<Boat> opt = boatRepo.findByUserIdAndId(user.getId(), id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        boatRepo.delete(opt.get());
        return ResponseEntity.ok("Boat deleted");
    }
}