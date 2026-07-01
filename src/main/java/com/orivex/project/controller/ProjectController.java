package com.orivex.project.controller;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.orivex.common.dto.PagedResponse;
import com.orivex.common.response.ApiResponse;
import com.orivex.project.dto.CreateProjectRequest;
import com.orivex.project.dto.ProjectResponse;
import com.orivex.project.enums.ProjectStatus;
import com.orivex.project.service.ProjectService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
@Validated
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ApiResponse<ProjectResponse> createProject(
            @Valid @RequestBody CreateProjectRequest request) {

        return projectService.createProject(request);

    }

    @GetMapping("/my")
    public ApiResponse<List<ProjectResponse>> getMyProjects() {

        return projectService.getMyProjects();

    }

    @GetMapping("/{id}")
    public ApiResponse<ProjectResponse> getProjectById(
            @PathVariable Long id) {

        return projectService.getProjectById(id);

    }

    @GetMapping("/paginated")
    public ApiResponse<PagedResponse<ProjectResponse>> getProjects(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "createdAt") String sortBy,
        @RequestParam(defaultValue = "desc") String direction,
        @RequestParam(required = false) ProjectStatus status) {

    return projectService.getProjects(
            page,
            size,
            sortBy,
            direction,
            status);

}

}