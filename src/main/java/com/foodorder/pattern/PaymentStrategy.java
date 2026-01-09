package com.foodorder.pattern;

import com.foodorder.enums.PaymentMethod;

public interface PaymentStrategy {
    boolean processPayment(Double amount, PaymentMethod method);
    PaymentMethod getPaymentMethod();
}
