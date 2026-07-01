package com.orivex.contract.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmitWorkRequest {

    @NotBlank(message = "Submission URL is required.")
    private String submissionUrl;

    @NotBlank(message = "Submission notes are required.")
    private String submissionNotes;

}