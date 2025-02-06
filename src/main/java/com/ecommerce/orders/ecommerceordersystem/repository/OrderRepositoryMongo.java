package com.ecommerce.orders.ecommerceordersystem.repository;

import com.ecommerce.orders.ecommerceordersystem.model.OrderMongo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface OrderRepositoryMongo extends ReactiveMongoRepository<OrderMongo, String> {
    Mono<OrderMongo> findByCustomerIdAndStatus(Long customerId, String status);
}