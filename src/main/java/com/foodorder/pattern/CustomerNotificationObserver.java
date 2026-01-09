package com.foodorder.pattern;

import com.foodorder.model.Order;
import org.springframework.stereotype.Component;

@Component
public class CustomerNotificationObserver implements OrderObserver {
    
    @Override
    public void onOrderStatusChanged(Order order) {
        // Notify customer about order status change
        System.out.println("Notifying customer " + order.getCustomer().getName() + 
                " - Order #" + order.getId() + " status changed to: " + order.getStatus());
    }
}
