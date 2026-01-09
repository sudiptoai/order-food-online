package com.foodorder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartDto {
    private Long customerId;
    private Long foodItemId;
    private Integer quantity;
}
