package com.ecommerce.orders.ecommerceordersystem.repository.integration;

import com.ecommerce.orders.ecommerceordersystem.config.TestConfig;
import com.ecommerce.orders.ecommerceordersystem.user.model.CustomerPsql;
import com.ecommerce.orders.ecommerceordersystem.user.repository.CustomerRepositoryPsql;
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
public class CustomerRepositoryPsqlIntegrationTest {

    @Autowired
    private CustomerRepositoryPsql customerRepository;

    @Test
    public void testFindByEmail() {
        String email = "test@example.com";
        Mono<CustomerPsql> result = customerRepository.findByEmail(email);

        assertNotNull(result);
    }
}