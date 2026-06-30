package com.orivex.contract.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.orivex.contract.enums.ContractStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContractResponse {

    private Long id;

    private String projectTitle;

    private String clientName;

    private String freelancerName;

    private BigDecimal agreedBudget;

    private LocalDate deadline;

    private ContractStatus status;

}