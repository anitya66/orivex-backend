package com.orivex.user.service;

import org.springframework.stereotype.Service;

import com.orivex.common.exception.BadRequestException;
import com.orivex.common.response.ApiResponse;
import com.orivex.security.AuthenticationFacade;
import com.orivex.user.dto.CreateFreelancerProfileRequest;
import com.orivex.user.dto.FreelancerProfileResponse;
import com.orivex.user.dto.UpdateFreelancerProfileRequest;
import com.orivex.user.entity.FreelancerProfile;
import com.orivex.user.entity.User;
import com.orivex.user.mapper.FreelancerProfileMapper;
import com.orivex.user.repository.FreelancerProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FreelancerProfileServiceImpl implements FreelancerProfileService {

    private final FreelancerProfileRepository freelancerProfileRepository;

    private final FreelancerProfileMapper freelancerProfileMapper;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public ApiResponse<FreelancerProfileResponse> createProfile(
            CreateFreelancerProfileRequest request) {

        User currentUser = authenticationFacade.getCurrentUser();

        if (freelancerProfileRepository.findByUser(currentUser).isPresent()) {

            throw new BadRequestException(
                    "Freelancer profile already exists.");

        }

        FreelancerProfile profile = freelancerProfileMapper.toEntity(request);

        profile.setUser(currentUser);

        FreelancerProfile savedProfile = freelancerProfileRepository.save(profile);

        FreelancerProfileResponse response = freelancerProfileMapper.toResponse(savedProfile);

        return ApiResponse.success(
                response,
                "Freelancer profile created successfully.");

    }

    @Override
    public ApiResponse<FreelancerProfileResponse> getMyProfile() {

        User currentUser = authenticationFacade.getCurrentUser();

        FreelancerProfile profile = freelancerProfileRepository.findByUser(currentUser)
                .orElseThrow(() -> new BadRequestException(
                        "Freelancer profile not found."));

        FreelancerProfileResponse response = freelancerProfileMapper.toResponse(profile);

        return ApiResponse.success(
                response,
                "Freelancer profile fetched successfully.");

    }

    @Override
    public ApiResponse<FreelancerProfileResponse> updateProfile(
            UpdateFreelancerProfileRequest request) {

        User currentUser = authenticationFacade.getCurrentUser();

        FreelancerProfile profile = freelancerProfileRepository.findByUser(currentUser)
                .orElseThrow(() -> new BadRequestException(
                        "Freelancer profile not found."));

        freelancerProfileMapper.updateEntity(
                request,
                profile);

        FreelancerProfile updatedProfile = freelancerProfileRepository.save(profile);

        FreelancerProfileResponse response = freelancerProfileMapper.toResponse(updatedProfile);

        return ApiResponse.success(
                response,
                "Freelancer profile updated successfully.");

    }

    @Override
    public ApiResponse<FreelancerProfileResponse> getProfileById(
            Long id) {

        FreelancerProfile profile = freelancerProfileRepository
                .findById(id)
                .orElseThrow(() -> new BadRequestException(
                        "Freelancer profile not found."));

        FreelancerProfileResponse response = freelancerProfileMapper.toResponse(profile);

        return ApiResponse.success(
                response,
                "Freelancer profile fetched successfully.");

    }

}