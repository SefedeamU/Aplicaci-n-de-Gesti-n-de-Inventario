package com.ecommerce.orders.ecommerceordersystem.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Table("orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderPsql {
    @Id
    private Long id;
    private Long customerId;
    private List<Long> productIds;
    private BigDecimal total;
    private String status;
    private LocalDateTime date;
    private String supplier;
}