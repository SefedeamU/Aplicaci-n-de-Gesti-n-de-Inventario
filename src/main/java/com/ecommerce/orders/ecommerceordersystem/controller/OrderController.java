package com.ecommerce.orders.ecommerceordersystem.controller;

import com.ecommerce.orders.ecommerceordersystem.model.OrderPsql;
import com.ecommerce.orders.ecommerceordersystem.service.transaction.TransactionService;
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
    public Flux<OrderPsql> getAllOrders() {
        return transactionService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Mono<OrderPsql> getOrderById(@PathVariable Long id) {
        return transactionService.getOrderById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<OrderPsql> createOrder(@RequestParam Long customerId, @RequestBody List<Long> productIds, @RequestParam String supplier) {
        return transactionService.createOrderWithTransaction(customerId, productIds)
                .flatMap(order -> {
                    order.setSupplier(supplier);
                    return transactionService.getOrderById(order.getId());
                });
    }

    @PostMapping("/{id}/confirm")
    public Mono<OrderPsql> confirmOrder(@PathVariable Long id) {
        return transactionService.checkout(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteOrder(@PathVariable Long id) {
        return transactionService.deleteOrder(id);
    }
}