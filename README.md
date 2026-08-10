# FisherConnect – Smart Fishing & Fisherman Support Platform

A full‑stack web application for managing fishing operations, catch records, expenses, marketplace, and orders.  
Designed for **fishermen**, **fish buyers**, and **administrators**.

![Landing Page](screenshots/landing.png)

## 📸 Screenshots

| Fisherman Dashboard | Active Trip | Marketplace |
|---------------------|-------------|-------------|
| ![Dashboard](screenshots/dashboard.png) | ![Active Trip](screenshots/active-trip.png) | ![Marketplace](screenshots/marketplace.png) |

## 🧰 Tech Stack

**Frontend**
- HTML5, CSS3, JavaScript (vanilla)
- Responsive design (ocean‑themed UI)
- Live Server for development

**Backend**
- Java 17+
- Spring Boot 3.2
- Spring Security + JWT + BCrypt
- Spring Data JPA (Hibernate)
- MySQL 8

**Tools**
- Maven, Git, GitHub, VS Code, Postman

## ✨ Features

- 🔐 JWT authentication & role‑based authorization (Fisherman, Buyer, Admin)
- 🚤 Boat management
- 🎣 Trip planning & active trip tracking (Planned → Active → Completed)
- 🐟 Catch records with automatic revenue calculation
- 💰 Expense logging (fuel, ice, food, etc.)
- 📈 Earnings dashboard with profit calculation
- 🏪 Fish marketplace – create listings, browse, place orders
- 📦 Order management (status updates, invoice preview)
- 🆘 Demo SOS / GPS (portfolio feature)
- 🔔 Database‑backed notifications

## 🚀 How to Run Locally

### Prerequisites

- Java 17+ & Maven
- Node.js (optional, for Live Server)
- MySQL 8+

### Backend

1. Create a MySQL database:  
   ```sql
   CREATE DATABASE fisherconnect;