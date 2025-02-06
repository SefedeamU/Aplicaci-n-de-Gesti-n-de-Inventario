package com.ecommerce.orders.ecommerceordersystem;

import org.springframework.boot.SpringApplication;

public class TestECommerceOrderSystemApplication {

    public static void main(String[] args) {
        SpringApplication.from(ECommerceOrderSystemApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
