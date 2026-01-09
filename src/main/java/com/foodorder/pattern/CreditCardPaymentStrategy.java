package com.foodorder.pattern;

import com.foodorder.enums.PaymentMethod;
import org.springframework.stereotype.Component;

@Component
public class CreditCardPaymentStrategy implements PaymentStrategy {
    
    @Override
    public boolean processPayment(Double amount, PaymentMethod method) {
        // Simulate credit card payment processing
        System.out.println("Processing credit card payment of Rs." + amount);
        return true;
    }
    
    @Override
    public PaymentMethod getPaymentMethod() {
        return PaymentMethod.CREDIT_CARD;
    }
}
