package com.orivex.contract.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.orivex.contract.dto.ContractResponse;
import com.orivex.contract.entity.Contract;

@Mapper(componentModel = "spring")
public interface ContractMapper {

    @Mapping(target = "projectTitle", source = "project.title")
    @Mapping(target = "clientName", source = "client.companyName")
    @Mapping(target = "freelancerName", source = "freelancer.user.name")
    ContractResponse toResponse(Contract contract);

}