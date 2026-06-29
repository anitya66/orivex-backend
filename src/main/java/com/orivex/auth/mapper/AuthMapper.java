package com.orivex.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.orivex.auth.dto.RegisterRequest;
import com.orivex.user.entity.User;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    @Mapping(target = "accountStatus", ignore = true)
    @Mapping(target = "freelancerProfile", ignore = true)
    User toUser(RegisterRequest request);

}