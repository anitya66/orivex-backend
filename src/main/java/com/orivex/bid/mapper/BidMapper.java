package com.orivex.bid.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.orivex.bid.dto.BidResponse;
import com.orivex.bid.dto.CreateBidRequest;
import com.orivex.bid.dto.UpdateBidRequest;
import com.orivex.bid.entity.Bid;

@Mapper(componentModel = "spring")
public interface BidMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "freelancer", ignore = true)
    @Mapping(target = "status", ignore = true)
    Bid toEntity(CreateBidRequest request);

    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "projectTitle", source = "project.title")
    @Mapping(target = "freelancerId", source = "freelancer.id")
    @Mapping(target = "freelancerName", source = "freelancer.user.name")
    @Mapping(target = "freelancerEmail", source = "freelancer.user.email")
    BidResponse toResponse(Bid bid);

    @Mapping(target = "id", ignore = true)
@Mapping(target = "createdAt", ignore = true)
@Mapping(target = "updatedAt", ignore = true)
@Mapping(target = "project", ignore = true)
@Mapping(target = "freelancer", ignore = true)
@Mapping(target = "status", ignore = true)
void updateEntity(
        UpdateBidRequest request,
        @MappingTarget Bid bid);
        

}