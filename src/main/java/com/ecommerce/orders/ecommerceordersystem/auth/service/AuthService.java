package com.ecommerce.orders.ecommerceordersystem.auth.service;

import com.ecommerce.orders.ecommerceordersystem.auth.dto.JwtAuthResponse;
import com.ecommerce.orders.ecommerceordersystem.auth.dto.LoginReq;
import com.ecommerce.orders.ecommerceordersystem.auth.dto.RegisterReq;
import com.ecommerce.orders.ecommerceordersystem.auth.exceptions.UserAlreadyExistException;
import com.ecommerce.orders.ecommerceordersystem.auth.exceptions.EmailNotRegisterdException;
import com.ecommerce.orders.ecommerceordersystem.auth.exceptions.IncorrectPasswordException;
import com.ecommerce.orders.ecommerceordersystem.user.model.CustomerPsql;
import com.ecommerce.orders.ecommerceordersystem.user.model.Role;
import com.ecommerce.orders.ecommerceordersystem.user.repository.CustomerRepositoryPsql;
import com.ecommerce.orders.ecommerceordersystem.user.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.ecommerce.orders.ecommerceordersystem.auth.security.JwtService;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final CustomerRepositoryPsql customerRepository;
    private final @Lazy PasswordEncoder passwordEncoder;
    private final JwtService jwtUtil;
    private final CustomerService customerService;

    public Mono<JwtAuthResponse> login(LoginReq req) {
        return customerRepository.findByEmail(req.getEmail())
                .switchIfEmpty(Mono.error(new EmailNotRegisterdException("Email is not registered")))
                .filter(user -> passwordEncoder.matches(req.getPassword(), user.getPassword()))
                .switchIfEmpty(Mono.error(new IncorrectPasswordException("Password is incorrect")))
                .flatMap(user -> customerService.loadUserByUsername(user.getEmail())
                        .map(userDetails -> {
                            JwtAuthResponse response = new JwtAuthResponse();
                            response.setToken(jwtUtil.generateToken(userDetails));
                            return response;
                        }));
    }

    public Mono<JwtAuthResponse> register(RegisterReq req) {
        return customerRepository.findByEmail(req.getEmail())
                .flatMap(existingUser -> Mono.<JwtAuthResponse>error(new UserAlreadyExistException("Email is already registered")))
                .switchIfEmpty(Mono.defer(() -> {
                    CustomerPsql customer = CustomerPsql.builder()
                            .name(req.getName())
                            .email(req.getEmail())
                            .password(passwordEncoder.encode(req.getPassword()))
                            .role(Role.USER)
                            .build();
                    return customerRepository.save(customer)
                            .flatMap(savedCustomer -> customerService.loadUserByUsername(savedCustomer.getEmail())
                                    .map(userDetails -> {
                                        JwtAuthResponse response = new JwtAuthResponse();
                                        response.setToken(jwtUtil.generateToken(userDetails));
                                        return response;
                                    }));
                }));
    }
}