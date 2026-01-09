package com.foodorder.service;

import com.foodorder.model.Restaurant;
import com.foodorder.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdminService {
    
    private final RestaurantService restaurantService;
    private final OrderService orderService;
    private final DeliveryService deliveryService;
    private final UserService userService;
    
    @Autowired
    public AdminService(RestaurantService restaurantService, OrderService orderService,
                        DeliveryService deliveryService, UserService userService) {
        this.restaurantService = restaurantService;
        this.orderService = orderService;
        this.deliveryService = deliveryService;
        this.userService = userService;
    }
    
    public void removeRestaurant(Long restaurantId) {
        restaurantService.removeRestaurant(restaurantId);
    }
    
    public void deleteRestaurant(Long restaurantId) {
        restaurantService.deleteRestaurant(restaurantId);
    }
    
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllActiveRestaurants();
    }
    
    public boolean checkOrderFeasibility(Long orderId) {
        return orderService.getOrderById(orderId)
                .map(order -> {
                    // Check if restaurant is active
                    boolean restaurantActive = order.getRestaurant().getActive();
                    
                    // Check if all food items are available
                    boolean itemsAvailable = order.getItems().stream()
                            .allMatch(item -> item.getFoodItem().getAvailable());
                    
                    // Check if delivery person is available if assigned
                    boolean deliveryAvailable = order.getDeliveryPerson() == null ||
                            deliveryService.getDeliveryPersonById(order.getDeliveryPerson().getId())
                                    .isPresent();
                    
                    return restaurantActive && itemsAvailable && deliveryAvailable;
                })
                .orElse(false);
    }
    
    public List<User> monitorDeliveryPersons() {
        return deliveryService.getAllDeliveryPersons().stream()
                .map(dp -> dp.getUser())
                .toList();
    }
}
