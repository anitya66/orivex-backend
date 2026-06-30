package com.orivex.project.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.orivex.project.enums.ExperienceLevel;
import com.orivex.project.enums.ProjectCategory;
import com.orivex.project.enums.ProjectStatus;
import com.orivex.project.enums.ProjectType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectResponse {

    private Long id;

    private String clientName;

    private String clientEmail;

    private String title;

    private String description;

    private ProjectCategory category;

    private ProjectType projectType;

    private ExperienceLevel experienceLevel;

    private BigDecimal budget;

    private LocalDate deadline;

    private ProjectStatus status;

}