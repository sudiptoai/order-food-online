package com.foodorder.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartDto {
    private Long customerId;
    private Long foodItemId;
    private Integer quantity;
}
