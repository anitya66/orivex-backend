package com.orivex.user.service;

import com.orivex.common.response.ApiResponse;
import com.orivex.user.dto.CreateFreelancerProfileRequest;
import com.orivex.user.dto.FreelancerProfileResponse;

public interface FreelancerProfileService {

    ApiResponse<FreelancerProfileResponse> createProfile(
            CreateFreelancerProfileRequest request);

    ApiResponse<FreelancerProfileResponse> getMyProfile();

}