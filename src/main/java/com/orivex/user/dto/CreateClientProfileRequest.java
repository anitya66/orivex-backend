package com.orivex.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateClientProfileRequest {

    @NotBlank(message = "Company name is required.")
    @Size(max = 150)
    private String companyName;

    @NotBlank(message = "Company description is required.")
    @Size(max = 1000)
    private String companyDescription;

    private String website;

    private String companyLogo;
}