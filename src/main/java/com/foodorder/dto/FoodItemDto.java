package com.foodorder.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodItemDto {
    private String name;
    private String description;
    private Double price;
    private Long restaurantId;
    private Boolean available;
    private String category;
    private Boolean vegetarian;
}
