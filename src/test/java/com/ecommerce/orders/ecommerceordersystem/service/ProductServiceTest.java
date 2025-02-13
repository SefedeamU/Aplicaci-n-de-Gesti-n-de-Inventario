package com.ecommerce.orders.ecommerceordersystem.service;

import com.ecommerce.orders.ecommerceordersystem.model.ProductPsql;
import com.ecommerce.orders.ecommerceordersystem.repository.ProductRepositoryPsql;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    @Mock
    private ProductRepositoryPsql productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(Flux.just(new ProductPsql(), new ProductPsql()));

        StepVerifier.create(productService.getAllProducts())
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    public void testGetProductById() {
        ProductPsql product = new ProductPsql();
        product.setId(1L);
        when(productRepository.findById(1L)).thenReturn(Mono.just(product));

        StepVerifier.create(productService.getProductById(1L))
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    public void testCreateProduct() {
        ProductPsql product = new ProductPsql();
        when(productRepository.save(any(ProductPsql.class))).thenReturn(Mono.just(product));

        StepVerifier.create(productService.createProduct(product))
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    public void testUpdateProduct() {
        ProductPsql existingProduct = new ProductPsql();
        existingProduct.setId(1L);
        ProductPsql updatedProduct = new ProductPsql();
        updatedProduct.setName("Updated Name");

        when(productRepository.findById(1L)).thenReturn(Mono.just(existingProduct));
        when(productRepository.save(any(ProductPsql.class))).thenReturn(Mono.just(updatedProduct));

        StepVerifier.create(productService.updateProduct(1L, updatedProduct))
                .expectNext(updatedProduct)
                .verifyComplete();
    }

    @Test
    public void testDeleteProduct() {
        when(productRepository.deleteById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(productService.deleteProduct(1L))
                .verifyComplete();
    }
}