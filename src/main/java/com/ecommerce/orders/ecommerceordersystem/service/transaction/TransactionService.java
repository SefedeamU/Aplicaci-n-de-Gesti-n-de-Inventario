package com.ecommerce.orders.ecommerceordersystem.service.transaction;

import com.ecommerce.orders.ecommerceordersystem.model.OrderPsql;
import com.ecommerce.orders.ecommerceordersystem.model.ProductPsql;
import com.ecommerce.orders.ecommerceordersystem.repository.OrderRepositoryPsql;
import com.ecommerce.orders.ecommerceordersystem.repository.ProductRepositoryPsql;
import com.ecommerce.orders.ecommerceordersystem.util.TransactionUtil;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final OrderRepositoryPsql orderRepository;
    private final ProductRepositoryPsql productRepository;
    private final TransactionUtil transactionUtil;

    public Flux<OrderPsql> getAllOrders() {
        return orderRepository.findAll();
    }

    public Mono<OrderPsql> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Mono<OrderPsql> createOrderWithTransaction(Long customerId, List<Long> productIds) {
        Publisher<Long> productIdsPublisher = Flux.fromIterable(productIds);
        return productRepository.findAllById(productIdsPublisher)
                .collectList()
                .flatMap(products -> {
                    if (products.isEmpty()) {
                        return Mono.error(new RuntimeException("No se encontraron productos válidos"));
                    }
                    var total = products.stream()
                            .map(ProductPsql::getPrice)
                            .reduce(BigDecimal::add)
                            .orElseThrow(() -> new RuntimeException("Error al calcular total"));
                    var order = OrderPsql.builder()
                            .customerId(customerId)
                            .productIds(productIds)
                            .total(total)
                            .status("PENDING")
                            .build();
                    return transactionUtil.executeTransaction(orderRepository.save(order));
                });
    }

    public Mono<OrderPsql> addProductToOrder(Long customerId, Long productId) {
        return transactionUtil.executeTransaction(
                orderRepository.findByCustomerIdAndStatus(customerId, "PENDING")
                        .switchIfEmpty(createOrder(customerId))
                        .flatMap(order -> productRepository.findById(productId)
                                .map(product -> {
                                    List<Long> productIds = new ArrayList<>(order.getProductIds());
                                    productIds.add(productId);
                                    order.setProductIds(productIds);
                                    order.setTotal(order.getTotal().add(product.getPrice()));
                                    return order;
                                })
                                .flatMap(orderRepository::save))
        );
    }

    public Mono<OrderPsql> removeProductFromOrder(Long customerId, Long productId) {
        return transactionUtil.executeTransaction(
                orderRepository.findByCustomerIdAndStatus(customerId, "PENDING")
                        .flatMap(order -> productRepository.findById(productId)
                                .map(product -> {
                                    List<Long> productIds = new ArrayList<>(order.getProductIds());
                                    productIds.remove(productId);
                                    order.setProductIds(productIds);
                                    order.setTotal(order.getTotal().subtract(product.getPrice()));
                                    return order;
                                })
                                .flatMap(orderRepository::save))
        );
    }

    public Mono<OrderPsql> checkout(Long customerId) {
        return transactionUtil.executeTransaction(
                orderRepository.findByCustomerIdAndStatus(customerId, "PENDING")
                        .flatMap(order -> {
                            order.setStatus("COMPLETED");
                            return orderRepository.save(order);
                        })
        );
    }

    private Mono<OrderPsql> createOrder(Long customerId) {
        OrderPsql order = OrderPsql.builder()
                .customerId(customerId)
                .productIds(List.of())
                .total(BigDecimal.ZERO)
                .status("PENDING")
                .build();
        return orderRepository.save(order);
    }

    public Mono<Void> deleteOrder(Long id) {
        return orderRepository.deleteById(id);
    }
}