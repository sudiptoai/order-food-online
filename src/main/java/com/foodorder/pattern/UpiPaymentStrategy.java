package com.foodorder.pattern;

import com.foodorder.enums.PaymentMethod;
import org.springframework.stereotype.Component;

@Component
public class UpiPaymentStrategy implements PaymentStrategy {
    
    @Override
    public boolean processPayment(Double amount, PaymentMethod method) {
        // Simulate UPI payment processing
        System.out.println("Processing UPI payment of Rs." + amount);
        return true;
    }
    
    @Override
    public PaymentMethod getPaymentMethod() {
        return PaymentMethod.UPI;
    }
}
