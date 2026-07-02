package com.orivex.project.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.orivex.common.dto.PagedResponse;
import com.orivex.common.response.ApiResponse;
import com.orivex.project.dto.CreateProjectRequest;
import com.orivex.project.dto.ProjectResponse;
import com.orivex.project.enums.ProjectStatus;
import com.orivex.project.service.ProjectService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Project Management", description = "APIs for managing projects")
@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @Operation(summary = "Create Project", description = "Creates a new project for the authenticated client.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Project created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation failed"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping
    public ApiResponse<ProjectResponse> createProject(
            @Valid @RequestBody CreateProjectRequest request) {

        return projectService.createProject(request);
    }

    @Operation(summary = "Get My Projects", description = "Returns all projects created by the authenticated client.")
    @GetMapping("/my")
    public ApiResponse<List<ProjectResponse>> getMyProjects() {

        return projectService.getMyProjects();
    }

    @Operation(summary = "Get Project By Id", description = "Returns project details by project id.")
    @GetMapping("/{id}")
    public ApiResponse<ProjectResponse> getProjectById(
            @PathVariable Long id) {

        return projectService.getProjectById(id);
    }

    @Operation(summary = "Get Projects", description = "Returns projects with pagination, sorting and filtering.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Projects fetched successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/paginated")
    public ApiResponse<PagedResponse<ProjectResponse>> getProjects(

            @Parameter(description = "Page number (starts from 0)") @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Number of records per page") @RequestParam(defaultValue = "5") int size,

            @Parameter(description = "Field used for sorting") @RequestParam(defaultValue = "createdAt") String sortBy,

            @Parameter(description = "Sorting direction (asc or desc)") @RequestParam(defaultValue = "desc") String direction,

            @Parameter(description = "Filter by project status") @RequestParam(required = false) ProjectStatus status,

            @Parameter(description = "Search keyword in project title") @RequestParam(required = false) String keyword,

            @Parameter(description = "Minimum project budget") @RequestParam(required = false) Double minBudget) {

        return projectService.getProjects(
                page,
                size,
                sortBy,
                direction,
                status,
                keyword,
                minBudget);
    }

    @Operation(summary = "Search Projects", description = "Search projects by title with pagination and sorting.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Projects fetched successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @GetMapping("/search")
    public ApiResponse<PagedResponse<ProjectResponse>> searchProjects(

            @Parameter(description = "Keyword to search") @RequestParam String keyword,

            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Page size") @RequestParam(defaultValue = "5") int size,

            @Parameter(description = "Sort field") @RequestParam(defaultValue = "createdAt") String sortBy,

            @Parameter(description = "Sort direction (asc or desc)") @RequestParam(defaultValue = "desc") String direction) {

        return projectService.searchProjects(
                keyword,
                page,
                size,
                sortBy,
                direction);
    }

}