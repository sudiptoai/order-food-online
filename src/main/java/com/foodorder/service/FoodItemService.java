package com.foodorder.service;

import com.foodorder.dto.FoodItemDto;
import com.foodorder.model.FoodItem;
import com.foodorder.model.Restaurant;
import com.foodorder.repository.FoodItemRepository;
import com.foodorder.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FoodItemService {
    
    private final FoodItemRepository foodItemRepository;
    private final RestaurantRepository restaurantRepository;
    
    @Autowired
    public FoodItemService(FoodItemRepository foodItemRepository, RestaurantRepository restaurantRepository) {
        this.foodItemRepository = foodItemRepository;
        this.restaurantRepository = restaurantRepository;
    }
    
    public FoodItem addFoodItem(FoodItemDto dto) {
        Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId())
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));
        
        FoodItem foodItem = new FoodItem();
        foodItem.setName(dto.getName());
        foodItem.setDescription(dto.getDescription());
        foodItem.setPrice(dto.getPrice());
        foodItem.setRestaurant(restaurant);
        foodItem.setAvailable(dto.getAvailable());
        foodItem.setCategory(dto.getCategory());
        foodItem.setVegetarian(dto.getVegetarian());
        
        return foodItemRepository.save(foodItem);
    }
    
    public List<FoodItem> getFoodItemsByRestaurant(Long restaurantId) {
        return foodItemRepository.findByRestaurantId(restaurantId);
    }
    
    public Optional<FoodItem> getFoodItemById(Long id) {
        return foodItemRepository.findById(id);
    }
    
    public List<FoodItem> searchFoodItemsByName(String name) {
        return foodItemRepository.searchByName(name);
    }
    
    public List<FoodItem> searchFoodItemsAcrossRestaurants(String itemName) {
        return foodItemRepository.searchFoodItemsAcrossRestaurants(itemName);
    }
    
    public FoodItem updateFoodItem(Long id, FoodItemDto dto) {
        FoodItem foodItem = foodItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Food item not found"));
        
        foodItem.setName(dto.getName());
        foodItem.setDescription(dto.getDescription());
        foodItem.setPrice(dto.getPrice());
        foodItem.setAvailable(dto.getAvailable());
        foodItem.setCategory(dto.getCategory());
        foodItem.setVegetarian(dto.getVegetarian());
        
        return foodItemRepository.save(foodItem);
    }
    
    public void updateAvailability(Long id, Boolean available) {
        FoodItem foodItem = foodItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Food item not found"));
        foodItem.setAvailable(available);
        foodItemRepository.save(foodItem);
    }
    
    public void deleteFoodItem(Long id) {
        foodItemRepository.deleteById(id);
    }
}
