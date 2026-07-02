package com.orivex.project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;

import com.orivex.common.exception.BadRequestException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.orivex.common.response.ApiResponse;
import com.orivex.project.dto.CreateProjectRequest;
import com.orivex.project.dto.ProjectResponse;
import com.orivex.project.entity.Project;
import com.orivex.project.mapper.ProjectMapper;
import com.orivex.project.repository.ProjectRepository;
import com.orivex.security.AuthenticationFacade;
import com.orivex.user.entity.ClientProfile;
import com.orivex.user.entity.User;
import com.orivex.user.repository.ClientProfileRepository;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMapper projectMapper;

    @Mock
    private AuthenticationFacade authenticationFacade;

    @Mock
    private ClientProfileRepository clientProfileRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Test
    void createProject_ShouldCreateProjectSuccessfully() {

        // =========================
        // Arrange
        // =========================

        CreateProjectRequest request = new CreateProjectRequest();
        request.setTitle("Netflix Backend");

        User user = new User();
        user.setEmail("client@gmail.com");

        ClientProfile client = new ClientProfile();

        Project project = new Project();

        ProjectResponse response = ProjectResponse.builder()
                .id(1L)
                .title("Netflix Backend")
                .build();

        when(authenticationFacade.getCurrentUser())
                .thenReturn(user);

        when(clientProfileRepository.findByUser(user))
                .thenReturn(Optional.of(client));

        when(projectMapper.toEntity(request))
                .thenReturn(project);

        when(projectRepository.save(project))
                .thenReturn(project);

        when(projectMapper.toResponse(project))
                .thenReturn(response);

        // =========================
        // Act
        // =========================

        ApiResponse<ProjectResponse> result = projectService.createProject(request);

        // =========================
        // Assert
        // =========================

        assertTrue(result.isSuccess());

        assertEquals(
                "Project created successfully.",
                result.getMessage());

        assertEquals(
                response,
                result.getData());

        verify(authenticationFacade).getCurrentUser();

        verify(clientProfileRepository).findByUser(user);

        verify(projectMapper).toEntity(request);

        verify(projectRepository).save(project);

        verify(projectMapper).toResponse(project);

    }

    @Test
void createProject_ShouldThrowException_WhenClientProfileNotFound() {

    // Arrange
    CreateProjectRequest request = new CreateProjectRequest();
    request.setTitle("Netflix Backend");

    User user = new User();
    user.setEmail("client@gmail.com");

    when(authenticationFacade.getCurrentUser())
            .thenReturn(user);

    when(clientProfileRepository.findByUser(user))
            .thenReturn(Optional.empty());

    // Act + Assert
    BadRequestException exception = assertThrows(
            BadRequestException.class,
            () -> projectService.createProject(request)
    );

    assertEquals(
            "Client profile not found.",
            exception.getMessage());

    verify(authenticationFacade).getCurrentUser();
    verify(clientProfileRepository).findByUser(user);

    verify(projectRepository, never()).save(any());
}

}