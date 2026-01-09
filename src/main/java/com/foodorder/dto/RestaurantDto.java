package com.foodorder.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDto {
    private String name;
    private String location;
    private String address;
    private String phone;
    private Long ownerId;
}
