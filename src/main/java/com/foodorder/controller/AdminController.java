package com.foodorder.controller;

import com.foodorder.model.Restaurant;
import com.foodorder.model.User;
import com.foodorder.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    
    private final AdminService adminService;
    
    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    
    @DeleteMapping("/restaurants/{restaurantId}")
    public ResponseEntity<Void> removeRestaurant(@PathVariable Long restaurantId) {
        adminService.removeRestaurant(restaurantId);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/restaurants/{restaurantId}/permanent")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long restaurantId) {
        adminService.deleteRestaurant(restaurantId);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/restaurants")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok(adminService.getAllRestaurants());
    }
    
    @GetMapping("/orders/{orderId}/feasibility")
    public ResponseEntity<Map<String, Boolean>> checkOrderFeasibility(@PathVariable Long orderId) {
        boolean feasible = adminService.checkOrderFeasibility(orderId);
        return ResponseEntity.ok(Map.of("feasible", feasible));
    }
    
    @GetMapping("/delivery-persons")
    public ResponseEntity<List<User>> monitorDeliveryPersons() {
        return ResponseEntity.ok(adminService.monitorDeliveryPersons());
    }
}
