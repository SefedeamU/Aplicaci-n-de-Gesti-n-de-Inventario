package com.ecommerce.orders.ecommerceordersystem.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderMongo {
    @Id
    private Long id;
    private Long customerId;
    private List<Long> productIds;
    private BigDecimal total;
    private String status;
}