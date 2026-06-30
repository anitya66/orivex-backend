package com.orivex.project.service;

import java.util.List;

import com.orivex.common.response.ApiResponse;
import com.orivex.project.dto.CreateProjectRequest;
import com.orivex.project.dto.ProjectResponse;

public interface ProjectService {

    ApiResponse<ProjectResponse> createProject(
            CreateProjectRequest request);

    ApiResponse<List<ProjectResponse>> getMyProjects();

    ApiResponse<ProjectResponse> getProjectById(
            Long id);

}