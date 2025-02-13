package com.ecommerce.orders.ecommerceordersystem.controller;

import com.ecommerce.orders.ecommerceordersystem.model.OrderPsql;
import com.ecommerce.orders.ecommerceordersystem.service.transaction.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class OrderControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private OrderController orderController;

    public OrderControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllOrders() {
        when(transactionService.getAllOrders()).thenReturn(Flux.just(new OrderPsql()));

        Flux<OrderPsql> response = orderController.getAllOrders();

        assertNotNull(response);
        verify(transactionService, times(1)).getAllOrders();
    }

    @Test
    public void testGetOrderById() {
        Long orderId = 1L;
        when(transactionService.getOrderById(orderId)).thenReturn(Mono.just(new OrderPsql()));

        Mono<OrderPsql> response = orderController.getOrderById(orderId);

        assertNotNull(response);
        verify(transactionService, times(1)).getOrderById(orderId);
    }

    @Test
    public void testCreateOrder() {
        Long customerId = 1L;
        List<Long> productIds = List.of(1L, 2L);
        String supplier = "SupplierName";
        OrderPsql order = new OrderPsql();
        when(transactionService.createOrderWithTransaction(customerId, productIds)).thenReturn(Mono.just(order));

        Mono<OrderPsql> response = orderController.createOrder(customerId, productIds, supplier);

        assertNotNull(response);
        verify(transactionService, times(1)).createOrderWithTransaction(customerId, productIds);
    }

    @Test
    public void testConfirmOrder() {
        Long orderId = 1L;
        OrderPsql order = new OrderPsql();
        when(transactionService.checkout(orderId)).thenReturn(Mono.just(order));

        Mono<OrderPsql> response = orderController.confirmOrder(orderId);

        assertNotNull(response);
        verify(transactionService, times(1)).checkout(orderId);
    }

    @Test
    public void testDeleteOrder() {
        Long orderId = 1L;
        when(transactionService.deleteOrder(orderId)).thenReturn(Mono.empty());

        Mono<Void> response = orderController.deleteOrder(orderId);

        assertNotNull(response);
        verify(transactionService, times(1)).deleteOrder(orderId);
    }
}