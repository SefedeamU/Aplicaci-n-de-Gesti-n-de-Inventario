package com.ecommerce.orders.ecommerceordersystem.service;

import com.ecommerce.orders.ecommerceordersystem.auth.dto.LoginReq;
import com.ecommerce.orders.ecommerceordersystem.auth.dto.RegisterReq;
import com.ecommerce.orders.ecommerceordersystem.auth.exceptions.EmailNotRegisterdException;
import com.ecommerce.orders.ecommerceordersystem.auth.exceptions.IncorrectPasswordException;
import com.ecommerce.orders.ecommerceordersystem.auth.exceptions.UserAlreadyExistException;
import com.ecommerce.orders.ecommerceordersystem.auth.security.JwtService;
import com.ecommerce.orders.ecommerceordersystem.auth.service.AuthService;
import com.ecommerce.orders.ecommerceordersystem.user.model.CustomerPsql;
import com.ecommerce.orders.ecommerceordersystem.user.repository.CustomerRepositoryPsql;
import com.ecommerce.orders.ecommerceordersystem.user.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthServiceTest {

    @Mock
    private CustomerRepositoryPsql customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogin() {
        LoginReq req = new LoginReq();
        req.setEmail("test@example.com");
        req.setPassword("password");

        CustomerPsql customer = new CustomerPsql();
        customer.setEmail("test@example.com");
        customer.setPassword("encodedPassword");

        when(customerRepository.findByEmail("test@example.com")).thenReturn(Mono.just(customer));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);
        when(customerService.loadUserByUsername("test@example.com")).thenReturn(Mono.just(customer));
        when(jwtService.generateToken(any())).thenReturn("token");

        StepVerifier.create(authService.login(req))
                .expectNextMatches(response -> response.getToken().equals("token"))
                .verifyComplete();
    }

    @Test
    public void testLoginEmailNotRegistered() {
        LoginReq req = new LoginReq();
        req.setEmail("test@example.com");
        req.setPassword("password");

        when(customerRepository.findByEmail("test@example.com")).thenReturn(Mono.empty());

        StepVerifier.create(authService.login(req))
                .expectError(EmailNotRegisterdException.class)
                .verify();
    }

    @Test
    public void testLoginIncorrectPassword() {
        LoginReq req = new LoginReq();
        req.setEmail("test@example.com");
        req.setPassword("password");

        CustomerPsql customer = new CustomerPsql();
        customer.setEmail("test@example.com");
        customer.setPassword("encodedPassword");

        when(customerRepository.findByEmail("test@example.com")).thenReturn(Mono.just(customer));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(false);

        StepVerifier.create(authService.login(req))
                .expectError(IncorrectPasswordException.class)
                .verify();
    }

    @Test
    public void testRegister() {
        RegisterReq req = new RegisterReq();
        req.setName("Test User");
        req.setEmail("test@example.com");
        req.setPassword("password");

        CustomerPsql customer = new CustomerPsql();
        customer.setEmail("test@example.com");

        when(customerRepository.findByEmail("test@example.com")).thenReturn(Mono.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(customerRepository.save(any(CustomerPsql.class))).thenReturn(Mono.just(customer));
        when(customerService.loadUserByUsername("test@example.com")).thenReturn(Mono.just(customer));
        when(jwtService.generateToken(any())).thenReturn("token");

        StepVerifier.create(authService.register(req))
                .expectNextMatches(response -> response.getToken().equals("token"))
                .verifyComplete();
    }

    @Test
    public void testRegisterUserAlreadyExists() {
        RegisterReq req = new RegisterReq();
        req.setName("Test User");
        req.setEmail("test@example.com");
        req.setPassword("password");

        when(customerRepository.findByEmail("test@example.com")).thenReturn(Mono.just(new CustomerPsql()));

        StepVerifier.create(authService.register(req))
                .expectError(UserAlreadyExistException.class)
                .verify();
    }
}