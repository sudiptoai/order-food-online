package com.foodorder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDto {
    private String name;
    private String location;
    private String address;
    private String phone;
    private Long ownerId;
}
