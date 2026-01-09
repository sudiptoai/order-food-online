package com.foodorder.dto;

import com.foodorder.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {
    private String name;
    private String email;
    private String password;
    private String phone;
    private UserRole role;
    private String address;
}
