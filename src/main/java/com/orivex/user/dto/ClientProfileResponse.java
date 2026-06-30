package com.orivex.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientProfileResponse {

    private Long id;

    private String name;

    private String email;

    private String companyName;

    private String companyDescription;

    private String website;

    private String companyLogo;
}