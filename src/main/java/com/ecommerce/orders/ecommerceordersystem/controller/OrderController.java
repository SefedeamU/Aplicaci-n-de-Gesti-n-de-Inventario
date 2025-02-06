package com.ecommerce.orders.ecommerceordersystem.controller;

import com.ecommerce.orders.ecommerceordersystem.model.OrderMongo;
import com.ecommerce.orders.ecommerceordersystem.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final TransactionService transactionService;

    @GetMapping
    public Flux<OrderMongo> getAllOrders() {
        return transactionService.getAllOrderMongos();
    }

    @GetMapping("/{id}")
    public Mono<OrderMongo> getOrderById(@PathVariable String id) {
        return transactionService.getOrderMongoById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<OrderMongo> createOrder(@RequestParam Long customerId, @RequestBody List<Long> productIds) {
        return transactionService.createOrderWithTransaction(customerId, productIds);
    }

    @PostMapping("/checkout")
    @ResponseStatus(HttpStatus.OK)
    public Mono<OrderMongo> checkout(@RequestParam Long customerId) {
        return transactionService.checkout(customerId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteOrder(@PathVariable String id) {
        return transactionService.deleteOrderMongo(id);
    }

    @DeleteMapping("/cart")
    @ResponseStatus(HttpStatus.OK)
    public Mono<OrderMongo> removeProductFromOrder(@RequestParam Long customerId, @RequestParam Long productId) {
        return transactionService.removeProductFromOrder(customerId, productId);
    }
}