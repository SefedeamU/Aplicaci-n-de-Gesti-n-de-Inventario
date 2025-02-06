package com.ecommerce.orders.ecommerceordersystem.repository;

import com.ecommerce.orders.ecommerceordersystem.model.ProductPsql;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepositoryPsql extends ReactiveCrudRepository<ProductPsql, Long> {
    Flux<ProductPsql> findAllById(Iterable<Long> ids);
}