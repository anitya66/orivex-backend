package com.orivex.user.service;

import com.orivex.common.response.ApiResponse;
import com.orivex.user.dto.CreateFreelancerProfileRequest;
import com.orivex.user.dto.FreelancerProfileResponse;
import com.orivex.user.dto.UpdateFreelancerProfileRequest;

public interface FreelancerProfileService {

    ApiResponse<FreelancerProfileResponse> createProfile(
            CreateFreelancerProfileRequest request);

    ApiResponse<FreelancerProfileResponse> getMyProfile();

    ApiResponse<FreelancerProfileResponse> updateProfile(
            UpdateFreelancerProfileRequest request);

    ApiResponse<FreelancerProfileResponse> getProfileById(
            Long id);

}