package com.fisherconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class FisherConnectApplication {

    public static void main(String[] args) {
        SpringApplication.run(FisherConnectApplication.class, args);
    }

    @GetMapping("/api/hello")
    public String hello() {
        return "FisherConnect backend is running!";
    }
}
