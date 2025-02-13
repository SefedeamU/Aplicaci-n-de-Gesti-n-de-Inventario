package com.ecommerce.orders.ecommerceordersystem.repository;

import com.ecommerce.orders.ecommerceordersystem.model.ProductPsql;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductRepositoryPsqlTest {

    @Mock
    private ProductRepositoryPsql productRepository;

    private ProductRepositoryPsqlTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllById() {
        Iterable<Long> ids = List.of(1L, 2L);
        when(productRepository.findAllById(ids)).thenReturn(Flux.just(new ProductPsql()));

        Flux<ProductPsql> result = productRepository.findAllById(ids);

        assertNotNull(result);
        verify(productRepository, times(1)).findAllById(ids);
    }
}