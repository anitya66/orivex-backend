package com.orivex.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.orivex.project.dto.CreateProjectRequest;
import com.orivex.project.dto.ProjectResponse;
import com.orivex.project.entity.Project;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "status", ignore = true)
    Project toEntity(CreateProjectRequest request);

    @Mapping(target = "clientName", source = "client.user.name")
    @Mapping(target = "clientEmail", source = "client.user.email")
    ProjectResponse toResponse(Project project);

}