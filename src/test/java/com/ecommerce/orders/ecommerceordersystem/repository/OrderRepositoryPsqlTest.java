package com.ecommerce.orders.ecommerceordersystem.repository;

import com.ecommerce.orders.ecommerceordersystem.model.OrderPsql;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class OrderRepositoryPsqlTest {

    @Mock
    private OrderRepositoryPsql orderRepository;

    private OrderRepositoryPsqlTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByCustomerIdAndStatus() {
        Long customerId = 1L;
        String status = "PENDING";
        OrderPsql order = new OrderPsql();
        when(orderRepository.findByCustomerIdAndStatus(customerId, status)).thenReturn(Mono.just(order));

        Mono<OrderPsql> result = orderRepository.findByCustomerIdAndStatus(customerId, status);

        assertNotNull(result);
        verify(orderRepository, times(1)).findByCustomerIdAndStatus(customerId, status);
    }
}