package com.orivex.project.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.orivex.common.response.ApiResponse;
import com.orivex.project.dto.ProjectResponse;
import com.orivex.project.service.ProjectService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProjectController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @Test
    void getProjectById_ShouldReturn200() throws Exception {

        ProjectResponse response = ProjectResponse.builder()
                .id(1L)
                .title("Netflix Backend")
                .build();

        ApiResponse<ProjectResponse> apiResponse = ApiResponse.success(response,
                "Project fetched successfully.");

        when(projectService.getProjectById(1L))
                .thenReturn(apiResponse);

        mockMvc.perform(get("/api/v1/projects/1"))
                .andExpect(status().isOk());

    }

}