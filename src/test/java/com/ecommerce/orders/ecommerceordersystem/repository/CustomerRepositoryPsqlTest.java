package com.ecommerce.orders.ecommerceordersystem.repository;

import com.ecommerce.orders.ecommerceordersystem.user.model.CustomerPsql;
import com.ecommerce.orders.ecommerceordersystem.user.repository.CustomerRepositoryPsql;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerRepositoryPsqlTest {

    @Mock
    private CustomerRepositoryPsql customerRepository;

    private CustomerRepositoryPsqlTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByEmail() {
        String email = "test@example.com";
        CustomerPsql customer = new CustomerPsql();
        when(customerRepository.findByEmail(email)).thenReturn(Mono.just(customer));

        Mono<CustomerPsql> result = customerRepository.findByEmail(email);

        assertNotNull(result);
        verify(customerRepository, times(1)).findByEmail(email);
    }
}