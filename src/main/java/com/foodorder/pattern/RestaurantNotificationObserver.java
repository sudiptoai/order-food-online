package com.foodorder.pattern;

import com.foodorder.model.Order;
import org.springframework.stereotype.Component;

@Component
public class RestaurantNotificationObserver implements OrderObserver {
    
    @Override
    public void onOrderStatusChanged(Order order) {
        // Notify restaurant about order status change
        System.out.println("Notifying restaurant " + order.getRestaurant().getName() + 
                " - Order #" + order.getId() + " status changed to: " + order.getStatus());
    }
}
