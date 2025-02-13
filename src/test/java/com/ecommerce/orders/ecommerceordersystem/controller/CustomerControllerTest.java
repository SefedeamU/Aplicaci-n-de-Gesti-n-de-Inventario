package com.ecommerce.orders.ecommerceordersystem.controller;

import com.ecommerce.orders.ecommerceordersystem.user.controller.CustomerController;
import com.ecommerce.orders.ecommerceordersystem.user.model.CustomerPsql;
import com.ecommerce.orders.ecommerceordersystem.user.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    public CustomerControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCustomers() {
        when(customerService.getAllCustomers()).thenReturn(Flux.just(new CustomerPsql()));

        Flux<CustomerPsql> response = customerController.getAllCustomers();

        assertNotNull(response);
        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    public void testGetCustomerById() {
        Long customerId = 1L;
        when(customerService.getCustomerById(customerId)).thenReturn(Mono.just(new CustomerPsql()));

        Mono<CustomerPsql> response = customerController.getCustomerById(customerId);

        assertNotNull(response);
        verify(customerService, times(1)).getCustomerById(customerId);
    }

    @Test
    public void testCreateCustomer() {
        CustomerPsql customer = new CustomerPsql();
        when(customerService.createCustomer(customer)).thenReturn(Mono.just(customer));

        Mono<CustomerPsql> response = customerController.createCustomer(customer);

        assertNotNull(response);
        verify(customerService, times(1)).createCustomer(customer);
    }

    @Test
    public void testUpdateCustomer() {
        Long customerId = 1L;
        CustomerPsql updatedCustomer = new CustomerPsql();
        when(customerService.updateCustomer(customerId, updatedCustomer)).thenReturn(Mono.just(updatedCustomer));

        Mono<CustomerPsql> response = customerController.updateCustomer(customerId, updatedCustomer);

        assertNotNull(response);
        verify(customerService, times(1)).updateCustomer(customerId, updatedCustomer);
    }

    @Test
    public void testDeleteCustomer() {
        Long customerId = 1L;
        when(customerService.deleteCustomer(customerId)).thenReturn(Mono.empty());

        Mono<Void> response = customerController.deleteCustomer(customerId);

        assertNotNull(response);
        verify(customerService, times(1)).deleteCustomer(customerId);
    }
}