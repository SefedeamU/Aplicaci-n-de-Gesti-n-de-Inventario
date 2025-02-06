package com.ecommerce.orders.ecommerceordersystem.auth.security;

import com.ecommerce.orders.ecommerceordersystem.user.model.Role;
import com.ecommerce.orders.ecommerceordersystem.user.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomerService customerService;

    @Bean
    public ReactiveUserDetailsService reactiveUserDetailsService() {
        return username -> customerService.findByUsername(username);
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/auth/**").permitAll()
                        .pathMatchers("/products").permitAll()
                        .pathMatchers("/products/{id}").permitAll()
                        .pathMatchers("/products/**").hasRole(Role.ADMIN.name())
                        .pathMatchers("/orders").hasRole(Role.USER.name())
                        .pathMatchers("/orders/{id}").hasRole(Role.USER.name())
                        .pathMatchers("/orders/cart").hasRole(Role.USER.name())
                        .pathMatchers("/orders/checkout").hasRole(Role.USER.name())
                        .anyExchange().authenticated()
                )
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
