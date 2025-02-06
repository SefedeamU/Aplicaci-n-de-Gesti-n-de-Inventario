package com.ecommerce.orders.ecommerceordersystem.user.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerPsql {
    @Id
    private Long id;
    private String name;
    private String email;
    private String password;
    private Role role;
}