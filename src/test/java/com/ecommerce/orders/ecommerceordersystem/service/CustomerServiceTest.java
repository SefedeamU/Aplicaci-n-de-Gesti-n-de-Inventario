package com.ecommerce.orders.ecommerceordersystem.service;

import com.ecommerce.orders.ecommerceordersystem.user.model.CustomerPsql;
import com.ecommerce.orders.ecommerceordersystem.user.repository.CustomerRepositoryPsql;
import com.ecommerce.orders.ecommerceordersystem.user.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    @Mock
    private CustomerRepositoryPsql customerRepositoryPsql;

    @InjectMocks
    private CustomerService customerService;

    private CustomerPsql customer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCustomers() {
        when(customerRepositoryPsql.findAll()).thenReturn(Flux.just(new CustomerPsql(), new CustomerPsql()));

        StepVerifier.create(customerService.getAllCustomers())
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    public void testGetCustomerById() {
        CustomerPsql customer = new CustomerPsql();
        customer.setId(1L);
        when(customerRepositoryPsql.findById(1L)).thenReturn(Mono.just(customer));

        StepVerifier.create(customerService.getCustomerById(1L))
                .expectNext(customer)
                .verifyComplete();
    }

    @Test
    public void testCreateCustomer() {
        CustomerPsql customer = new CustomerPsql();
        when(customerRepositoryPsql.save(any(CustomerPsql.class))).thenReturn(Mono.just(customer));

        StepVerifier.create(customerService.createCustomer(customer))
                .expectNext(customer)
                .verifyComplete();
    }

    @Test
    public void testUpdateCustomer() {
        CustomerPsql existingCustomer = new CustomerPsql();
        existingCustomer.setId(1L);
        CustomerPsql updatedCustomer = new CustomerPsql();
        updatedCustomer.setName("Updated Name");

        when(customerRepositoryPsql.findById(1L)).thenReturn(Mono.just(existingCustomer));
        when(customerRepositoryPsql.save(any(CustomerPsql.class))).thenReturn(Mono.just(updatedCustomer));

        StepVerifier.create(customerService.updateCustomer(1L, updatedCustomer))
                .expectNext(updatedCustomer)
                .verifyComplete();
    }

    @Test
    public void testDeleteCustomer() {
        when(customerRepositoryPsql.deleteById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(customerService.deleteCustomer(1L))
                .verifyComplete();
    }

    @Test
    public void testFindByUsername() {
        CustomerPsql customer = new CustomerPsql();
        customer.setEmail("test@example.com");
        when(customerRepositoryPsql.findByEmail("test@example.com")).thenReturn(Mono.just(customer));

        StepVerifier.create(customerService.loadUserByUsername("test@example.com"))
                .expectNextMatches(userDetails -> userDetails.getUsername().equals("test@example.com"))
                .verifyComplete();
    }

    @Test
    public void testLoadUserByUsername() {
        when(customerRepositoryPsql.findByEmail("test@example.com")).thenReturn(Mono.empty());

        StepVerifier.create(customerService.loadUserByUsername("test@example.com"))
                .expectError(UsernameNotFoundException.class)
                .verify();
    }
}