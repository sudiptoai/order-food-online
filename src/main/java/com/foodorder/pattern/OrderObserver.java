package com.foodorder.pattern;

import com.foodorder.model.Order;

public interface OrderObserver {
    void onOrderStatusChanged(Order order);
}
