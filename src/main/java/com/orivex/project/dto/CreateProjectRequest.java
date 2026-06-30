package com.orivex.project.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.orivex.project.enums.ExperienceLevel;
import com.orivex.project.enums.ProjectCategory;
import com.orivex.project.enums.ProjectType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateProjectRequest {

    @NotBlank(message = "Title is required.")
    @Size(max = 200)
    private String title;

    @NotBlank(message = "Description is required.")
    @Size(max = 5000)
    private String description;

    @NotNull(message = "Category is required.")
    private ProjectCategory category;

    @NotNull(message = "Project type is required.")
    private ProjectType projectType;

    @NotNull(message = "Experience level is required.")
    private ExperienceLevel experienceLevel;

    @NotNull(message = "Budget is required.")
    @DecimalMin(value = "1.0", message = "Budget must be greater than zero.")
    private BigDecimal budget;

    @NotNull(message = "Deadline is required.")
    @Future(message = "Deadline must be in the future.")
    private LocalDate deadline;

}