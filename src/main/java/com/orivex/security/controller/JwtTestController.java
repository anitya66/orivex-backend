package com.orivex.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orivex.security.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class JwtTestController {

    private final JwtService jwtService;

    @GetMapping("/api/v1/test/token")
    public String generateToken() {

        return jwtService.generateToken("anitya@gmail.com");

    }

}