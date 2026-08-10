package com.fisherconnect.controller;

import com.fisherconnect.entity.User;
import com.fisherconnect.repository.UserRepository;
import com.fisherconnect.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepo.findByEmail(user.getEmail()).isPresent())
            return ResponseEntity.badRequest().body("Email already exists");
        user.setPassword(encoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now().toString());
        userRepo.save(user);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        var optUser = userRepo.findByEmail(email);
        if (optUser.isEmpty() || !encoder.matches(password, optUser.get().getPassword()))
            return ResponseEntity.status(401).body("Invalid credentials");
        User user = optUser.get();
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return ResponseEntity.ok(Map.of(
            "token", token,
            "id", user.getId(),
            "name", user.getName(),
            "role", user.getRole()
        ));
    }
}