package com.fisherconnect.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fisherconnect.entity.FishListing;
import com.fisherconnect.entity.FishOrder;
import com.fisherconnect.entity.User;
import com.fisherconnect.repository.FishListingRepository;
import com.fisherconnect.repository.FishOrderRepository;
import com.fisherconnect.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class MarketplaceController {

    @Autowired
    private FishListingRepository listingRepo;

    @Autowired
    private FishOrderRepository orderRepo;

    @Autowired
    private UserRepository userRepo;

    // Fisherman creates a listing
    @PostMapping("/fisherman/listings")
    public ResponseEntity<FishListing> createListing(@RequestBody FishListing listing, Principal principal) {
        User user = userRepo.findByEmail(principal.getName()).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();
        listing.setUser(user);
        listing.setStatus("ACTIVE");
        listing.setCreatedAt(LocalDateTime.now());
        return ResponseEntity.ok(listingRepo.save(listing));
    }

    // Fisherman views their own listings
    @GetMapping("/fisherman/listings")
    public ResponseEntity<List<FishListing>> getMyListings(Principal principal) {
        User user = userRepo.findByEmail(principal.getName()).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(listingRepo.findByUserId(user.getId()));
    }

    // Buyer browses active listings
    @GetMapping("/buyer/listings")
    public ResponseEntity<List<FishListing>> browseListings() {
        return ResponseEntity.ok(listingRepo.findByStatus("ACTIVE"));
    }

    // Buyer places an order
    @PostMapping("/buyer/orders")
    public ResponseEntity<FishOrder> placeOrder(@RequestBody FishOrder order, Principal principal) {
        User buyer = userRepo.findByEmail(principal.getName()).orElse(null);
        if (buyer == null) return ResponseEntity.notFound().build();
        FishListing listing = listingRepo.findById(order.getListing().getId()).orElse(null);
        if (listing == null || !"ACTIVE".equals(listing.getStatus())) 
            return ResponseEntity.badRequest().build();
        if (order.getQuantityKg() > listing.getQuantityKg())
            return ResponseEntity.badRequest().build();
        order.setBuyer(buyer);
        order.setSeller(listing.getUser());
        order.setTotalPrice(order.getQuantityKg() * listing.getPricePerKg());
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());
        // Update listing quantity
        listing.setQuantityKg(listing.getQuantityKg() - order.getQuantityKg());
        if (listing.getQuantityKg() <= 0) listing.setStatus("SOLD");
        listingRepo.save(listing);
        return ResponseEntity.ok(orderRepo.save(order));
    }

    // Buyer views their orders
    @GetMapping("/buyer/orders")
    public ResponseEntity<List<FishOrder>> getBuyerOrders(Principal principal) {
        User buyer = userRepo.findByEmail(principal.getName()).orElse(null);
        if (buyer == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderRepo.findByBuyerId(buyer.getId()));
    }

    // Fisherman views orders for their listings
    @GetMapping("/fisherman/orders")
    public ResponseEntity<List<FishOrder>> getSellerOrders(Principal principal) {
        User seller = userRepo.findByEmail(principal.getName()).orElse(null);
        if (seller == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderRepo.findBySellerId(seller.getId()));
    }

    // Update order status
    @PutMapping("/fisherman/orders/{id}")
    public ResponseEntity<FishOrder> updateOrderStatus(@PathVariable Long id, @RequestBody FishOrder update, Principal principal) {
        FishOrder order = orderRepo.findById(id).orElse(null);
        if (order == null) return ResponseEntity.notFound().build();
        order.setStatus(update.getStatus());
        return ResponseEntity.ok(orderRepo.save(order));
    }
}