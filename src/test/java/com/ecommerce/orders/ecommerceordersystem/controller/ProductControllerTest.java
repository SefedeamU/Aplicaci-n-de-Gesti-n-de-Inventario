package com.ecommerce.orders.ecommerceordersystem.controller;

import com.ecommerce.orders.ecommerceordersystem.model.ProductPsql;
import com.ecommerce.orders.ecommerceordersystem.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    public ProductControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllProducts() {
        when(productService.getAllProducts()).thenReturn(Flux.just(new ProductPsql()));

        Flux<ProductPsql> response = productController.getAllProducts();

        assertNotNull(response);
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    public void testGetProductById() {
        Long productId = 1L;
        when(productService.getProductById(productId)).thenReturn(Mono.just(new ProductPsql()));

        Mono<ProductPsql> response = productController.getProductById(productId);

        assertNotNull(response);
        verify(productService, times(1)).getProductById(productId);
    }

    @Test
    public void testCreateProduct() {
        ProductPsql product = new ProductPsql();
        when(productService.createProduct(product)).thenReturn(Mono.just(product));

        Mono<ProductPsql> response = productController.createProduct(product);

        assertNotNull(response);
        verify(productService, times(1)).createProduct(product);
    }

    @Test
    public void testUpdateProduct() {
        Long productId = 1L;
        ProductPsql updatedProduct = new ProductPsql();
        when(productService.updateProduct(productId, updatedProduct)).thenReturn(Mono.just(updatedProduct));

        Mono<ProductPsql> response = productController.updateProduct(productId, updatedProduct);

        assertNotNull(response);
        verify(productService, times(1)).updateProduct(productId, updatedProduct);
    }

    @Test
    public void testDeleteProduct() {
        Long productId = 1L;
        when(productService.deleteProduct(productId)).thenReturn(Mono.empty());

        Mono<Void> response = productController.deleteProduct(productId);

        assertNotNull(response);
        verify(productService, times(1)).deleteProduct(productId);
    }
}