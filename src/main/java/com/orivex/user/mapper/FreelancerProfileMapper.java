package com.orivex.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.orivex.user.dto.CreateFreelancerProfileRequest;
import com.orivex.user.dto.FreelancerProfileResponse;
import com.orivex.user.dto.UpdateFreelancerProfileRequest;
import com.orivex.user.entity.FreelancerProfile;

@Mapper(componentModel = "spring")
public interface FreelancerProfileMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    FreelancerProfile toEntity(CreateFreelancerProfileRequest request);

    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "email", source = "user.email")
    FreelancerProfileResponse toResponse(FreelancerProfile profile);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateEntity(
            UpdateFreelancerProfileRequest request,
            @MappingTarget FreelancerProfile profile);

}