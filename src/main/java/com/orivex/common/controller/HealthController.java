package com.orivex.common.controller;

import com.orivex.common.dto.HealthResponse;
import com.orivex.common.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health")
public class HealthController {

    @GetMapping
    public ApiResponse<HealthResponse> health() {

    HealthResponse response =
            HealthResponse.builder()
                    .application("Orivex")
                    .version("v1.0.0")
                    .status("UP")
                    .build();

    return ApiResponse.success(
            response,
            "Orivex Backend is running successfully."
    );
    }

}