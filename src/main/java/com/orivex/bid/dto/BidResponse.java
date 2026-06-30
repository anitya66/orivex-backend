package com.orivex.bid.dto;

import java.math.BigDecimal;

import com.orivex.bid.enums.BidStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BidResponse {

    private Long id;

    private Long projectId;

    private String projectTitle;

    private Long freelancerId;

    private String freelancerName;

    private String freelancerEmail;

    private String coverLetter;

    private BigDecimal proposedBudget;

    private Integer estimatedDays;

    private BidStatus status;

}