package com.ecommerce.orders.ecommerceordersystem.repository.integration;

import com.ecommerce.orders.ecommerceordersystem.config.TestConfig;
import com.ecommerce.orders.ecommerceordersystem.model.OrderPsql;
import com.ecommerce.orders.ecommerceordersystem.repository.OrderRepositoryPsql;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
@TestPropertySource(locations = "classpath:.env")
public class OrderRepositoryPsqlIntegrationTest {

    @Autowired
    private OrderRepositoryPsql orderRepository;

    @Test
    public void testFindByCustomerIdAndStatus() {
        Long customerId = 1L;
        String status = "PENDING";
        Mono<OrderPsql> result = orderRepository.findByCustomerIdAndStatus(customerId, status);

        assertNotNull(result);
    }
}