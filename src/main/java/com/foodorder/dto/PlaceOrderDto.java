package com.foodorder.dto;

import com.foodorder.enums.PaymentMethod;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrderDto {
    private Long customerId;
    private Long restaurantId;
    private PaymentMethod paymentMethod;
    private String deliveryAddress;
}
