package com.orivex.user.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateFreelancerProfileRequest {

    @NotBlank(message = "Headline is required.")
    @Size(max = 150)
    private String headline;

    @NotBlank(message = "Bio is required.")
    @Size(max = 1000)
    private String bio;

    @DecimalMin(value = "0.0", message = "Hourly rate must be positive.")
    private BigDecimal hourlyRate;

    @NotBlank(message = "Experience is required.")
    private String experience;

    private String skills;

    private String portfolioUrl;

    private String githubUrl;

    private String linkedinUrl;

    private String profileImage;

    private Boolean available;

}