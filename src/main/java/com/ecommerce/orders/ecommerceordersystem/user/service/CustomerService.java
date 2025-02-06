package com.ecommerce.orders.ecommerceordersystem.user.service;

import com.ecommerce.orders.ecommerceordersystem.user.model.CustomerPsql;
import com.ecommerce.orders.ecommerceordersystem.user.repository.CustomerRepositoryPsql;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepositoryPsql customerRepositoryPsql;

    public Flux<CustomerPsql> getAllCustomers() {
        return customerRepositoryPsql.findAll();
    }

    public Mono<CustomerPsql> getCustomerById(Long id) {
        return customerRepositoryPsql.findById(id);
    }

    public Mono<CustomerPsql> createCustomer(CustomerPsql customer) {
        return customerRepositoryPsql.save(customer);
    }

    public Mono<CustomerPsql> updateCustomer(Long id, CustomerPsql updatedCustomer) {
        return customerRepositoryPsql.findById(id)
                .flatMap(existingCustomer -> {
                    existingCustomer.setName(updatedCustomer.getName());
                    existingCustomer.setEmail(updatedCustomer.getEmail());
                    return customerRepositoryPsql.save(existingCustomer);
                });
    }

    public Mono<Void> deleteCustomer(Long id) {
        return customerRepositoryPsql.deleteById(id);
    }

    public Mono<UserDetails> findByUsername(String username) {
        return customerRepositoryPsql.findByEmail(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found")))
                .cast(UserDetails.class);
    }

    public Mono<UserDetails> loadUserByUsername(String username) {
        return findByUsername(username);
    }


}
