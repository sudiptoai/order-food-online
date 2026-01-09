package com.foodorder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
