package com.ecommerce.orders.ecommerceordersystem.user.repository;

import com.ecommerce.orders.ecommerceordersystem.user.model.CustomerPsql;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface CustomerRepositoryPsql extends ReactiveCrudRepository<CustomerPsql, Long> {
    Mono<CustomerPsql> findByEmail(String email);
}