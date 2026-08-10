package com.fisherconnect.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/fisherman/test")
    public ResponseEntity<String> fishermanTest() {
        return ResponseEntity.ok("Hello Fisherman! You are authenticated.");
    }

    @GetMapping("/api/buyer/test")
    public ResponseEntity<String> buyerTest() {
        return ResponseEntity.ok("Hello Buyer! You are authenticated.");
    }

    @GetMapping("/api/admin/test")
    public ResponseEntity<String> adminTest() {
        return ResponseEntity.ok("Hello Admin! You are authenticated.");
    }
}
