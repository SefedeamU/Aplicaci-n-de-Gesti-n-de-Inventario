package com.ecommerce.orders.ecommerceordersystem.service;

import com.ecommerce.orders.ecommerceordersystem.model.OrderMongo;
import com.ecommerce.orders.ecommerceordersystem.repository.OrderRepositoryMongo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepositoryMongo orderMongoRepository;

    public Flux<OrderMongo> getAllOrderMongos() {
        return orderMongoRepository.findAll();
    }

    public Mono<OrderMongo> getOrderMongoById(String id) {
        return orderMongoRepository.findById(id);
    }

    public Mono<Void> deleteOrderMongo(String id) {
        return orderMongoRepository.deleteById(id);
    }
}