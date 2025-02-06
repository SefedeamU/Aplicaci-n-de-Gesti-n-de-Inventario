package com.ecommerce.orders.ecommerceordersystem.auth.controller;

import com.ecommerce.orders.ecommerceordersystem.auth.dto.JwtAuthResponse;
import com.ecommerce.orders.ecommerceordersystem.auth.dto.LoginReq;
import com.ecommerce.orders.ecommerceordersystem.auth.dto.RegisterReq;
import com.ecommerce.orders.ecommerceordersystem.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public Mono<ResponseEntity<JwtAuthResponse>> login(@RequestBody LoginReq loginReq) {
        return authService.login(loginReq)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<JwtAuthResponse>> register(@RequestBody RegisterReq req) {
        return authService.register(req)
                .map(ResponseEntity::ok);
    }
}