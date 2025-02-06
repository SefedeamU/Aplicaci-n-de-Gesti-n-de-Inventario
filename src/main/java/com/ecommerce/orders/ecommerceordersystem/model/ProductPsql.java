package com.ecommerce.orders.ecommerceordersystem.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPsql {
    @Id
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
}