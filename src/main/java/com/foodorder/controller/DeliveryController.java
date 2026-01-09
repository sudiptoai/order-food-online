package com.foodorder.controller;

import com.foodorder.enums.DeliveryStatus;
import com.foodorder.model.DeliveryPerson;
import com.foodorder.model.Order;
import com.foodorder.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {
    
    private final DeliveryService deliveryService;
    
    @Autowired
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<DeliveryPerson> registerDeliveryPerson(@RequestBody Map<String, Object> request) {
        Long userId = Long.valueOf(request.get("userId").toString());
        String vehicleNumber = request.get("vehicleNumber").toString();
        
        DeliveryPerson deliveryPerson = deliveryService.registerDeliveryPerson(userId, vehicleNumber);
        return new ResponseEntity<>(deliveryPerson, HttpStatus.CREATED);
    }
    
    @GetMapping("/available")
    public ResponseEntity<List<DeliveryPerson>> getAvailableDeliveryPersons() {
        return ResponseEntity.ok(deliveryService.getAvailableDeliveryPersons());
    }
    
    @PatchMapping("/{deliveryPersonId}/status")
    public ResponseEntity<DeliveryPerson> updateDeliveryStatus(
            @PathVariable Long deliveryPersonId, @RequestParam DeliveryStatus status) {
        DeliveryPerson deliveryPerson = deliveryService.updateDeliveryStatus(deliveryPersonId, status);
        return ResponseEntity.ok(deliveryPerson);
    }
    
    @PatchMapping("/{deliveryPersonId}/location")
    public ResponseEntity<DeliveryPerson> updateLocation(
            @PathVariable Long deliveryPersonId, @RequestParam String location) {
        DeliveryPerson deliveryPerson = deliveryService.updateLocation(deliveryPersonId, location);
        return ResponseEntity.ok(deliveryPerson);
    }
    
    @GetMapping("/{deliveryPersonId}/orders")
    public ResponseEntity<List<Order>> getDeliveryPersonOrders(@PathVariable Long deliveryPersonId) {
        return ResponseEntity.ok(deliveryService.getDeliveryPersonOrders(deliveryPersonId));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DeliveryPerson> getDeliveryPersonById(@PathVariable Long id) {
        return deliveryService.getDeliveryPersonById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping
    public ResponseEntity<List<DeliveryPerson>> getAllDeliveryPersons() {
        return ResponseEntity.ok(deliveryService.getAllDeliveryPersons());
    }
}
