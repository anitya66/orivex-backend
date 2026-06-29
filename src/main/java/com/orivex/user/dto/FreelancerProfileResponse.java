package com.orivex.user.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FreelancerProfileResponse {

    private Long id;

    private String name;

    private String email;

    private String headline;

    private String bio;

    private BigDecimal hourlyRate;

    private String experience;

    private String skills;

    private String portfolioUrl;

    private String githubUrl;

    private String linkedinUrl;

    private String profileImage;

    private Boolean available;

}