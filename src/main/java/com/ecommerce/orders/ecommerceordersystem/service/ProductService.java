package com.ecommerce.orders.ecommerceordersystem.service;

import com.ecommerce.orders.ecommerceordersystem.model.ProductPsql;
import com.ecommerce.orders.ecommerceordersystem.repository.ProductRepositoryPsql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepositoryPsql productRepository;

    public Flux<ProductPsql> getAllProducts() {
        return productRepository.findAll();
    }

    public Mono<ProductPsql> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Mono<ProductPsql> createProduct(ProductPsql product) {
        return productRepository.save(product);
    }

    public Mono<ProductPsql> updateProduct(Long id, ProductPsql updatedProduct) {
        return productRepository.findById(id)
                .flatMap(existingProduct -> {
                    existingProduct.setName(updatedProduct.getName());
                    existingProduct.setDescription(updatedProduct.getDescription());
                    existingProduct.setPrice(updatedProduct.getPrice());
                    return productRepository.save(existingProduct);
                });
    }

    public Mono<Void> deleteProduct(Long id) {
        return productRepository.deleteById(id);
    }
}