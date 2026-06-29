package com.orivex.auth.service;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.orivex.auth.dto.LoginRequest;
import com.orivex.auth.dto.LoginResponse;
import com.orivex.auth.dto.RegisterRequest;
import com.orivex.auth.mapper.AuthMapper;
import com.orivex.common.exception.BadRequestException;
import com.orivex.common.response.ApiResponse;
import com.orivex.security.JwtService;
import com.orivex.user.entity.User;
import com.orivex.user.enums.AccountStatus;
import com.orivex.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final AuthMapper authMapper;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Override
    public ApiResponse<String> register(RegisterRequest request) {

        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

        if (existingUser.isPresent()) {

            throw new BadRequestException(
                    "Email is already registered.");

        }

        User user = authMapper.toUser(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setAccountStatus(AccountStatus.PENDING_VERIFICATION);

        userRepository.save(user);

        return ApiResponse.success(
                "User Registered Successfully.");

    }

    @Override
    public ApiResponse<LoginResponse> login(
            LoginRequest request) {

        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(

                        request.getEmail(),

                        request.getPassword()

                )

        );

        User user = userRepository.findByEmail(
                request.getEmail()).orElseThrow(
                        () -> new BadRequestException(
                                "Invalid email or password."));

        if (user.getAccountStatus() != AccountStatus.ACTIVE
                && user.getAccountStatus() != AccountStatus.PENDING_VERIFICATION) {

            throw new BadRequestException(
                    "Account is not active.");
        }

        String token = jwtService.generateToken(
                user.getEmail());

        LoginResponse response = LoginResponse.builder()

                .accessToken(token)

                .tokenType("Bearer")

                .build();

        return ApiResponse.success(
                response,
                "Login Successful.");

    }

}