package com.orivex.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.orivex.user.dto.ClientProfileResponse;
import com.orivex.user.dto.CreateClientProfileRequest;
import com.orivex.user.entity.ClientProfile;

@Mapper(componentModel = "spring")
public interface ClientProfileMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    ClientProfile toEntity(CreateClientProfileRequest request);

    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "email", source = "user.email")
    ClientProfileResponse toResponse(ClientProfile profile);

}