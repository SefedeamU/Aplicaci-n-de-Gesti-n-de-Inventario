package com.ecommerce.orders.ecommerceordersystem.transaction;

import com.ecommerce.orders.ecommerceordersystem.model.OrderMongo;
import com.ecommerce.orders.ecommerceordersystem.model.ProductPsql;
import com.ecommerce.orders.ecommerceordersystem.repository.OrderRepositoryMongo;
import com.ecommerce.orders.ecommerceordersystem.repository.ProductRepositoryPsql;
import com.ecommerce.orders.ecommerceordersystem.util.TransactionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final OrderRepositoryMongo orderRepository;
    private final ProductRepositoryPsql productRepository;
    private final TransactionUtil transactionUtil;

    public Flux<OrderMongo> getAllOrderMongos() {
        return orderRepository.findAll();
    }

    public Mono<OrderMongo> getOrderMongoById(String id) {
        return orderRepository.findById(id);
    }

    public Mono<OrderMongo> createOrderWithTransaction(Long customerId, List<Long> productIds) {
        return productRepository.findAllById(productIds)
                .collectList()
                .flatMap(products -> {
                    if (products.isEmpty()) {
                        return Mono.error(new RuntimeException("No se encontraron productos válidos"));
                    }
                    var total = products.stream()
                            .map(ProductPsql::getPrice)
                            .reduce(BigDecimal::add)
                            .orElseThrow(() -> new RuntimeException("Error al calcular total"));
                    var order = OrderMongo.builder()
                            .customerId(customerId)
                            .productIds(productIds)
                            .total(total)
                            .status("PENDING")
                            .build();
                    return transactionUtil.executeTransaction(orderRepository.save(order));
                });
    }

    public Mono<OrderMongo> addProductToOrder(Long customerId, Long productId) {
        return transactionUtil.executeTransaction(
                orderRepository.findByCustomerIdAndStatus(customerId, "PENDING")
                        .switchIfEmpty(createOrder(customerId))
                        .flatMap(order -> productRepository.findById(productId)
                                .map(product -> {
                                    order.getProductIds().add(productId);
                                    order.setTotal(order.getTotal().add(product.getPrice()));
                                    return order;
                                })
                                .flatMap(orderRepository::save))
        );
    }

    public Mono<OrderMongo> removeProductFromOrder(Long customerId, Long productId) {
        return transactionUtil.executeTransaction(
                orderRepository.findByCustomerIdAndStatus(customerId, "PENDING")
                        .flatMap(order -> productRepository.findById(productId)
                                .map(product -> {
                                    order.getProductIds().remove(productId);
                                    order.setTotal(order.getTotal().subtract(product.getPrice()));
                                    return order;
                                })
                                .flatMap(orderRepository::save))
        );
    }

    public Mono<OrderMongo> checkout(Long customerId) {
        return transactionUtil.executeTransaction(
                orderRepository.findByCustomerIdAndStatus(customerId, "PENDING")
                        .flatMap(order -> {
                            order.setStatus("COMPLETED");
                            return orderRepository.save(order);
                        })
        );
    }

    private Mono<OrderMongo> createOrder(Long customerId) {
        OrderMongo order = OrderMongo.builder()
                .customerId(customerId)
                .productIds(List.of())
                .total(BigDecimal.ZERO)
                .status("PENDING")
                .build();
        return orderRepository.save(order);
    }

    public Mono<Void> deleteOrderMongo(String id) {
        return orderRepository.deleteById(id);
    }
}