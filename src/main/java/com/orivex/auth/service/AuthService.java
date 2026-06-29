package com.orivex.auth.service;

import com.orivex.auth.dto.RegisterRequest;
import com.orivex.common.response.ApiResponse;
import com.orivex.auth.dto.LoginRequest;
import com.orivex.auth.dto.LoginResponse;

public interface AuthService {

    ApiResponse<String> register(RegisterRequest request);

    ApiResponse<LoginResponse> login(LoginRequest request);

}