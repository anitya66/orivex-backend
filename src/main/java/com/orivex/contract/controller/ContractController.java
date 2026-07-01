package com.orivex.contract.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orivex.common.response.ApiResponse;
import com.orivex.contract.dto.ContractResponse;
import com.orivex.contract.dto.SubmitWorkRequest;
import com.orivex.contract.service.ContractService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    // Freelancer - View My Contracts
    @GetMapping("/my")
    public ApiResponse<List<ContractResponse>> getMyContracts() {

        return contractService.getMyContracts();

    }

    // Client - View My Contracts
    @GetMapping("/client")
    public ApiResponse<List<ContractResponse>> getClientContracts() {

        return contractService.getClientContracts();

    }

    // Get Contract Details
    @GetMapping("/{contractId}")
    public ApiResponse<ContractResponse> getContractById(
            @PathVariable Long contractId) {

        return contractService.getContractById(contractId);

    }

    // Freelancer Starts Contract
    @PutMapping("/{contractId}/start")
    public ApiResponse<String> startContract(
            @PathVariable Long contractId) {

        return contractService.startContract(contractId);

    }

    // Freelancer Submits Work
    @PutMapping("/{contractId}/submit")
    public ApiResponse<String> submitWork(
            @PathVariable Long contractId,
            @Valid @RequestBody SubmitWorkRequest request) {

        return contractService.submitWork(contractId, request);

    }

    @PutMapping("/{contractId}/approve")
    public ApiResponse<String> approveContract(
            @PathVariable Long contractId) {

        return contractService.approveContract(contractId);

    }

}