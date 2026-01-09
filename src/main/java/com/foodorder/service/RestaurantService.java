package com.foodorder.service;

import com.foodorder.dto.RestaurantDto;
import com.foodorder.model.Restaurant;
import com.foodorder.model.User;
import com.foodorder.repository.RestaurantRepository;
import com.foodorder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RestaurantService {
    
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    
    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }
    
    public Restaurant addRestaurant(RestaurantDto dto) {
        User owner = userRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new IllegalArgumentException("Owner not found"));
        
        Restaurant restaurant = new Restaurant();
        restaurant.setName(dto.getName());
        restaurant.setLocation(dto.getLocation());
        restaurant.setAddress(dto.getAddress());
        restaurant.setPhone(dto.getPhone());
        restaurant.setOwner(owner);
        restaurant.setActive(true);
        
        return restaurantRepository.save(restaurant);
    }
    
    public List<Restaurant> getAllActiveRestaurants() {
        return restaurantRepository.findByActiveTrue();
    }
    
    public Optional<Restaurant> getRestaurantById(Long id) {
        return restaurantRepository.findById(id);
    }
    
    public List<Restaurant> searchRestaurantsByName(String name) {
        return restaurantRepository.findByNameContainingIgnoreCase(name);
    }
    
    public List<Restaurant> searchRestaurantsByLocation(String location) {
        return restaurantRepository.findByLocationContainingIgnoreCase(location);
    }
    
    public List<Restaurant> searchRestaurantsByNameAndLocation(String name, String location) {
        return restaurantRepository.searchByNameAndLocation(name, location);
    }
    
    public Restaurant updateRestaurant(Long id, RestaurantDto dto) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));
        
        restaurant.setName(dto.getName());
        restaurant.setLocation(dto.getLocation());
        restaurant.setAddress(dto.getAddress());
        restaurant.setPhone(dto.getPhone());
        
        return restaurantRepository.save(restaurant);
    }
    
    public void removeRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));
        restaurant.setActive(false);
        restaurantRepository.save(restaurant);
    }
    
    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }
}
