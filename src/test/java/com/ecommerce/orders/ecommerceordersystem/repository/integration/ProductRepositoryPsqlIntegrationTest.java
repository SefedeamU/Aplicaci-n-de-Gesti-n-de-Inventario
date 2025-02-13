package com.ecommerce.orders.ecommerceordersystem.repository.integration;

import com.ecommerce.orders.ecommerceordersystem.config.TestConfig;
import com.ecommerce.orders.ecommerceordersystem.model.ProductPsql;
import com.ecommerce.orders.ecommerceordersystem.repository.ProductRepositoryPsql;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
@TestPropertySource(locations = "classpath:.env")
public class ProductRepositoryPsqlIntegrationTest {

    @Autowired
    private ProductRepositoryPsql productRepository;

    @Test
    public void testFindAllById() {
        Iterable<Long> ids = List.of(1L, 2L);
        Flux<ProductPsql> result = productRepository.findAllById(ids);

        assertNotNull(result);
    }
}