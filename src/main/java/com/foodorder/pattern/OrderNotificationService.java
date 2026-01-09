package com.foodorder.pattern;

import com.foodorder.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderNotificationService {
    
    private final List<OrderObserver> observers = new ArrayList<>();
    
    @Autowired
    public OrderNotificationService(List<OrderObserver> orderObservers) {
        this.observers.addAll(orderObservers);
    }
    
    public void notifyObservers(Order order) {
        for (OrderObserver observer : observers) {
            observer.onOrderStatusChanged(order);
        }
    }
}
