package com.foodorder.pattern;

import com.foodorder.enums.PaymentMethod;
import org.springframework.stereotype.Component;

@Component
public class CashOnDeliveryStrategy implements PaymentStrategy {
    
    @Override
    public boolean processPayment(Double amount, PaymentMethod method) {
        // Cash on delivery - no immediate payment processing
        System.out.println("Order placed with Cash on Delivery. Amount: Rs." + amount);
        return true;
    }
    
    @Override
    public PaymentMethod getPaymentMethod() {
        return PaymentMethod.CASH_ON_DELIVERY;
    }
}
