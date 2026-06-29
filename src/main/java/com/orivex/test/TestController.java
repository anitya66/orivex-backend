package com.orivex.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orivex.security.AuthenticationFacade;
import com.orivex.user.entity.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final AuthenticationFacade authenticationFacade;

    @GetMapping("/api/v1/test/me")
    public String me() {

        User user = authenticationFacade.getCurrentUser();

        return "Logged in as: " + user.getEmail();

    }

}