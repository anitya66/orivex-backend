package com.orivex.user.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.orivex.common.response.ApiResponse;
import com.orivex.user.dto.ClientProfileResponse;
import com.orivex.user.dto.CreateClientProfileRequest;
import com.orivex.user.service.ClientProfileService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
@Validated
public class ClientProfileController {

    private final ClientProfileService clientProfileService;

    @PostMapping("/profile")
    public ApiResponse<ClientProfileResponse> createProfile(
            @Valid @RequestBody CreateClientProfileRequest request) {

        return clientProfileService.createProfile(request);

    }

    @GetMapping("/profile")
    public ApiResponse<ClientProfileResponse> getMyProfile() {

        return clientProfileService.getMyProfile();

    }

    @GetMapping("/profile/{id}")
    public ApiResponse<ClientProfileResponse> getProfileById(
            @PathVariable Long id) {

        return clientProfileService.getProfileById(id);

    }

}