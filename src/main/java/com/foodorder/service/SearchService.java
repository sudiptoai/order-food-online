package com.foodorder.service;

import com.foodorder.dto.SearchDto;
import com.foodorder.model.FoodItem;
import com.foodorder.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SearchService {
    
    private final RestaurantService restaurantService;
    private final FoodItemService foodItemService;
    
    @Autowired
    public SearchService(RestaurantService restaurantService, FoodItemService foodItemService) {
        this.restaurantService = restaurantService;
        this.foodItemService = foodItemService;
    }
    
    public List<Restaurant> searchRestaurants(SearchDto searchDto) {
        if (searchDto.getQuery() != null && searchDto.getLocation() != null) {
            return restaurantService.searchRestaurantsByNameAndLocation(
                    searchDto.getQuery(), searchDto.getLocation());
        } else if (searchDto.getQuery() != null) {
            return restaurantService.searchRestaurantsByName(searchDto.getQuery());
        } else if (searchDto.getLocation() != null) {
            return restaurantService.searchRestaurantsByLocation(searchDto.getLocation());
        }
        return restaurantService.getAllActiveRestaurants();
    }
    
    public List<FoodItem> searchFoodItems(String itemName) {
        return foodItemService.searchFoodItemsAcrossRestaurants(itemName);
    }
}
