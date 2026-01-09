package com.foodorder.controller;

import com.foodorder.dto.RestaurantDto;
import com.foodorder.model.Restaurant;
import com.foodorder.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    
    private final RestaurantService restaurantService;
    
    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }
    
    @PostMapping
    public ResponseEntity<Restaurant> addRestaurant(@RequestBody RestaurantDto dto) {
        Restaurant restaurant = restaurantService.addRestaurant(dto);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllActiveRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllActiveRestaurants());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        return restaurantService.getRestaurantById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/search/name/{name}")
    public ResponseEntity<List<Restaurant>> searchByName(@PathVariable String name) {
        return ResponseEntity.ok(restaurantService.searchRestaurantsByName(name));
    }
    
    @GetMapping("/search/location/{location}")
    public ResponseEntity<List<Restaurant>> searchByLocation(@PathVariable String location) {
        return ResponseEntity.ok(restaurantService.searchRestaurantsByLocation(location));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long id, @RequestBody RestaurantDto dto) {
        Restaurant restaurant = restaurantService.updateRestaurant(id, dto);
        return ResponseEntity.ok(restaurant);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }
}
