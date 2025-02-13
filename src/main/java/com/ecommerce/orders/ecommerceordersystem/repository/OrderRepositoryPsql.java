package com.ecommerce.orders.ecommerceordersystem.repository;

import com.ecommerce.orders.ecommerceordersystem.model.OrderPsql;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface OrderRepositoryPsql extends ReactiveCrudRepository<OrderPsql, Long> {
    Mono<OrderPsql> findByCustomerIdAndStatus(Long customerId, String status);
}