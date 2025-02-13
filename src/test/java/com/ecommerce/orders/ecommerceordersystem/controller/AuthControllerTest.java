package com.ecommerce.orders.ecommerceordersystem.controller;

import com.ecommerce.orders.ecommerceordersystem.auth.controller.AuthController;
import com.ecommerce.orders.ecommerceordersystem.auth.dto.JwtAuthResponse;
import com.ecommerce.orders.ecommerceordersystem.auth.dto.LoginReq;
import com.ecommerce.orders.ecommerceordersystem.auth.dto.RegisterReq;
import com.ecommerce.orders.ecommerceordersystem.auth.service.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    public AuthControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogin() {
        LoginReq loginReq = new LoginReq();
        loginReq.setEmail("test@example.com");
        loginReq.setPassword("password");

        when(authService.login(loginReq)).thenReturn(Mono.just(new JwtAuthResponse()));

        Mono<ResponseEntity<JwtAuthResponse>> response = authController.login(loginReq);

        assertNotNull(response);
        verify(authService, times(1)).login(loginReq);
    }

    @Test
    public void testRegister() {
        RegisterReq registerReq = new RegisterReq();
        registerReq.setEmail("test@example.com");
        registerReq.setPassword("password");

        when(authService.register(registerReq)).thenReturn(Mono.just(new JwtAuthResponse()));

        Mono<ResponseEntity<JwtAuthResponse>> response = authController.register(registerReq);

        assertNotNull(response);
        verify(authService, times(1)).register(registerReq);
    }
}