package com.fisherconnect.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "boats")
public class Boat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String regNumber;
    private String type;
    private Integer length;
    private Integer capacity;
    private String status;
    private String createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Boat() {}

    public Boat(Long id, String name, String regNumber, String type, Integer length, Integer capacity, String status, String createdAt, User user) {
        this.id = id;
        this.name = name;
        this.regNumber = regNumber;
        this.type = type;
        this.length = length;
        this.capacity = capacity;
        this.status = status;
        this.createdAt = createdAt;
        this.user = user;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRegNumber() { return regNumber; }
    public void setRegNumber(String regNumber) { this.regNumber = regNumber; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Integer getLength() { return length; }
    public void setLength(Integer length) { this.length = length; }
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}