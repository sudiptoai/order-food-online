package com.foodorder.controller;

import com.foodorder.dto.FoodItemDto;
import com.foodorder.model.FoodItem;
import com.foodorder.service.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food-items")
public class FoodItemController {
    
    private final FoodItemService foodItemService;
    
    @Autowired
    public FoodItemController(FoodItemService foodItemService) {
        this.foodItemService = foodItemService;
    }
    
    @PostMapping
    public ResponseEntity<FoodItem> addFoodItem(@RequestBody FoodItemDto dto) {
        FoodItem foodItem = foodItemService.addFoodItem(dto);
        return new ResponseEntity<>(foodItem, HttpStatus.CREATED);
    }
    
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<FoodItem>> getFoodItemsByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(foodItemService.getFoodItemsByRestaurant(restaurantId));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<FoodItem> getFoodItemById(@PathVariable Long id) {
        return foodItemService.getFoodItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/search/{name}")
    public ResponseEntity<List<FoodItem>> searchFoodItems(@PathVariable String name) {
        return ResponseEntity.ok(foodItemService.searchFoodItemsByName(name));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<FoodItem> updateFoodItem(@PathVariable Long id, @RequestBody FoodItemDto dto) {
        FoodItem foodItem = foodItemService.updateFoodItem(id, dto);
        return ResponseEntity.ok(foodItem);
    }
    
    @PatchMapping("/{id}/availability")
    public ResponseEntity<Void> updateAvailability(@PathVariable Long id, @RequestParam Boolean available) {
        foodItemService.updateAvailability(id, available);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoodItem(@PathVariable Long id) {
        foodItemService.deleteFoodItem(id);
        return ResponseEntity.noContent().build();
    }
}
