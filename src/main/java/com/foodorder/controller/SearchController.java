package com.foodorder.controller;

import com.foodorder.dto.SearchDto;
import com.foodorder.model.FoodItem;
import com.foodorder.model.Restaurant;
import com.foodorder.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    
    private final SearchService searchService;
    
    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }
    
    @PostMapping("/restaurants")
    public ResponseEntity<List<Restaurant>> searchRestaurants(@RequestBody SearchDto searchDto) {
        return ResponseEntity.ok(searchService.searchRestaurants(searchDto));
    }
    
    @GetMapping("/food-items/{itemName}")
    public ResponseEntity<List<FoodItem>> searchFoodItems(@PathVariable String itemName) {
        return ResponseEntity.ok(searchService.searchFoodItems(itemName));
    }
}
