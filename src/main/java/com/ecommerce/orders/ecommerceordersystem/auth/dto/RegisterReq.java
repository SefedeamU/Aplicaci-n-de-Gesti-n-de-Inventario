package com.ecommerce.orders.ecommerceordersystem.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterReq {
    private String name;
    private String email;
    private String password;
    private String phone;
}