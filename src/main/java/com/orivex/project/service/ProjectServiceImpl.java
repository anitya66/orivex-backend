package com.orivex.project.service;

import com.orivex.common.dto.PagedResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import com.orivex.common.exception.BadRequestException;
import com.orivex.common.response.ApiResponse;
import com.orivex.project.dto.CreateProjectRequest;
import com.orivex.project.dto.ProjectResponse;
import com.orivex.project.entity.Project;
import com.orivex.project.enums.ProjectStatus;
import com.orivex.project.mapper.ProjectMapper;
import com.orivex.project.repository.ProjectRepository;
import com.orivex.security.AuthenticationFacade;
import com.orivex.user.entity.ClientProfile;
import com.orivex.user.entity.User;
import com.orivex.user.repository.ClientProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final ProjectMapper projectMapper;

    private final AuthenticationFacade authenticationFacade;

    private final ClientProfileRepository clientProfileRepository;

    @Override
    public ApiResponse<ProjectResponse> createProject(
            CreateProjectRequest request) {

        User currentUser = authenticationFacade.getCurrentUser();

        ClientProfile clientProfile = clientProfileRepository
                .findByUser(currentUser)
                .orElseThrow(() -> new BadRequestException(
                        "Client profile not found."));

        Project project = projectMapper.toEntity(request);

        project.setClient(clientProfile);

        project.setStatus(ProjectStatus.OPEN);

        Project savedProject = projectRepository.save(project);

        ProjectResponse response = projectMapper.toResponse(savedProject);

        return ApiResponse.success(
                response,
                "Project created successfully.");

    }

    @Override
    public ApiResponse<List<ProjectResponse>> getMyProjects() {

        User currentUser = authenticationFacade.getCurrentUser();

        ClientProfile clientProfile = clientProfileRepository
                .findByUser(currentUser)
                .orElseThrow(() -> new BadRequestException(
                        "Client profile not found."));

        List<ProjectResponse> response = projectRepository
                .findByClient(clientProfile)
                .stream()
                .map(projectMapper::toResponse)
                .toList();

        return ApiResponse.success(
                response,
                "Projects fetched successfully.");

    }

    @Override
    public ApiResponse<ProjectResponse> getProjectById(
                    Long id) {

            Project project = projectRepository
                            .findById(id)
                            .orElseThrow(() -> new BadRequestException(
                                            "Project not found."));

            return ApiResponse.success(
                            projectMapper.toResponse(project),
                            "Project fetched successfully.");

    }
    
    @Override
    public ApiResponse<PagedResponse<ProjectResponse>> getProjects(
                    int page,
                    int size,
                    String sortBy,
                    String direction,
                    ProjectStatus status) {

            Sort sort = direction.equalsIgnoreCase("desc")
                            ? Sort.by(sortBy).descending()
                            : Sort.by(sortBy).ascending();

            Pageable pageable = PageRequest.of(
                            page,
                            size,
                            sort);

            Page<Project> projects;

            if (status == null) {

                    projects = projectRepository.findAll(pageable);

            } else {

                    projects = projectRepository.findByStatus(
                                    status,
                                    pageable);

            }

            Page<ProjectResponse> projectPage = projects.map(
                            projectMapper::toResponse);

            PagedResponse<ProjectResponse> response = PagedResponse.<ProjectResponse>builder()
                            .content(projectPage.getContent())
                            .page(projectPage.getNumber())
                            .size(projectPage.getSize())
                            .totalItems(projectPage.getTotalElements())
                            .totalPages(projectPage.getTotalPages())
                            .hasNext(projectPage.hasNext())
                            .hasPrevious(projectPage.hasPrevious())
                            .build();

            return ApiResponse.success(
                            response,
                            "Projects fetched successfully.");

    }

}