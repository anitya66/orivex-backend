package com.orivex.review.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReviewResponse {

    private Long id;

    private Long contractId;

    private String reviewerName;

    private String revieweeName;

    private Integer rating;

    private String comment;

    private LocalDate createdAt;

}