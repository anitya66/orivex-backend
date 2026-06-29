package com.orivex.auth.controller;

import com.orivex.auth.dto.LoginRequest;
import com.orivex.auth.dto.LoginResponse;
import com.orivex.auth.dto.RegisterRequest;
import com.orivex.auth.service.AuthService;
import com.orivex.common.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


   @PostMapping("/register")
    public ApiResponse<String> register(
            @Valid @RequestBody RegisterRequest request) {

        return authService.register(request);
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(
        @Valid @RequestBody LoginRequest request) {

    return authService.login(request);

}


}