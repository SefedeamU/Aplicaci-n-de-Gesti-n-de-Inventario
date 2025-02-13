package com.ecommerce.orders.ecommerceordersystem.service;

import com.ecommerce.orders.ecommerceordersystem.model.OrderPsql;
import com.ecommerce.orders.ecommerceordersystem.model.ProductPsql;
import com.ecommerce.orders.ecommerceordersystem.repository.OrderRepositoryPsql;
import com.ecommerce.orders.ecommerceordersystem.repository.ProductRepositoryPsql;
import com.ecommerce.orders.ecommerceordersystem.service.transaction.TransactionService;
import com.ecommerce.orders.ecommerceordersystem.util.TransactionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TransactionServiceTest {

    @Mock
    private OrderRepositoryPsql orderRepository;

    @Mock
    private ProductRepositoryPsql productRepository;

    @Mock
    private TransactionUtil transactionUtil;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllOrders() {
        when(orderRepository.findAll()).thenReturn(Flux.just(new OrderPsql(), new OrderPsql()));

        StepVerifier.create(transactionService.getAllOrders())
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    public void testGetOrderById() {
        OrderPsql order = new OrderPsql();
        order.setId(1L);
        when(orderRepository.findById(1L)).thenReturn(Mono.just(order));

        StepVerifier.create(transactionService.getOrderById(1L))
                .expectNext(order)
                .verifyComplete();
    }

    @Test
    public void testCreateOrderWithTransaction() {
        ProductPsql product = new ProductPsql();
        product.setId(1L);
        product.setPrice(BigDecimal.TEN);

        when(productRepository.findAllById(any(Publisher.class))).thenReturn(Flux.just(product));
        when(orderRepository.save(any(OrderPsql.class))).thenReturn(Mono.just(new OrderPsql()));
        when(transactionUtil.executeTransaction(any(Mono.class))).thenAnswer(invocation -> invocation.getArgument(0));

        StepVerifier.create(transactionService.createOrderWithTransaction(1L, List.of(1L)))
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    public void testAddProductToOrder() {
        OrderPsql order = new OrderPsql();
        order.setProductIds(List.of(1L));
        order.setTotal(BigDecimal.TEN);
        ProductPsql product = new ProductPsql();
        product.setId(2L);
        product.setPrice(BigDecimal.TEN);

        when(orderRepository.findByCustomerIdAndStatus(1L, "PENDING")).thenReturn(Mono.just(order));
        when(productRepository.findById(2L)).thenReturn(Mono.just(product));
        when(orderRepository.save(any(OrderPsql.class))).thenReturn(Mono.just(order));
        when(transactionUtil.executeTransaction(any(Mono.class))).thenAnswer(invocation -> invocation.getArgument(0));

        StepVerifier.create(transactionService.addProductToOrder(1L, 2L))
                .expectNext(order)
                .verifyComplete();
    }

    @Test
    public void testRemoveProductFromOrder() {
        OrderPsql order = new OrderPsql();
        order.setProductIds(List.of(1L, 2L));
        order.setTotal(BigDecimal.TEN);
        ProductPsql product = new ProductPsql();
        product.setId(2L);
        product.setPrice(BigDecimal.TEN);

        when(orderRepository.findByCustomerIdAndStatus(1L, "PENDING")).thenReturn(Mono.just(order));
        when(productRepository.findById(2L)).thenReturn(Mono.just(product));
        when(orderRepository.save(any(OrderPsql.class))).thenReturn(Mono.just(order));
        when(transactionUtil.executeTransaction(any(Mono.class))).thenAnswer(invocation -> invocation.getArgument(0));

        StepVerifier.create(transactionService.removeProductFromOrder(1L, 2L))
                .expectNext(order)
                .verifyComplete();
    }

    @Test
    public void testCheckout() {
        OrderPsql order = new OrderPsql();
        order.setStatus("PENDING");

        OrderPsql completedOrder = new OrderPsql();
        completedOrder.setStatus("COMPLETED");

        when(orderRepository.findByCustomerIdAndStatus(1L, "PENDING")).thenReturn(Mono.just(order));
        when(orderRepository.save(any(OrderPsql.class))).thenReturn(Mono.just(completedOrder));
        when(transactionUtil.executeTransaction(any(Mono.class))).thenAnswer(invocation -> invocation.getArgument(0));

        StepVerifier.create(transactionService.checkout(1L))
                .expectNextMatches(savedOrder -> "COMPLETED".equals(savedOrder.getStatus()))
                .verifyComplete();
    }

    @Test
    public void testDeleteOrder() {
        when(orderRepository.deleteById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(transactionService.deleteOrder(1L))
                .verifyComplete();
    }
}