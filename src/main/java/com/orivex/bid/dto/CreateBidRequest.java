package com.orivex.bid.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateBidRequest {

    @NotNull(message = "Project ID is required.")
    private Long projectId;

    @NotBlank(message = "Cover letter is required.")
    @Size(max = 2000)
    private String coverLetter;

    @NotNull(message = "Proposed budget is required.")
    @DecimalMin(value = "1.0", message = "Budget must be greater than zero.")
    private BigDecimal proposedBudget;

    @NotNull(message = "Estimated days is required.")
    @Positive(message = "Estimated days must be greater than zero.")
    private Integer estimatedDays;

}