package com.foodorder.pattern;

import com.foodorder.enums.PaymentMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PaymentStrategyFactory {
    
    private final Map<PaymentMethod, PaymentStrategy> strategies = new HashMap<>();
    
    @Autowired
    public PaymentStrategyFactory(List<PaymentStrategy> paymentStrategies) {
        for (PaymentStrategy strategy : paymentStrategies) {
            strategies.put(strategy.getPaymentMethod(), strategy);
        }
    }
    
    public PaymentStrategy getStrategy(PaymentMethod method) {
        PaymentStrategy strategy = strategies.get(method);
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported payment method: " + method);
        }
        return strategy;
    }
}
