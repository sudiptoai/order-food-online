package com.foodorder.dto;

import com.foodorder.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrderDto {
    private Long customerId;
    private Long restaurantId;
    private PaymentMethod paymentMethod;
    private String deliveryAddress;
}
