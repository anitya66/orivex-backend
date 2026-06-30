package com.orivex.contract.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orivex.common.response.ApiResponse;
import com.orivex.contract.dto.ContractResponse;
import com.orivex.contract.service.ContractService;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @GetMapping("/my")
    public ApiResponse<List<ContractResponse>> getMyContracts() {

        return contractService.getMyContracts();

    }

    @GetMapping("/client")
    public ApiResponse<List<ContractResponse>> getClientContracts() {

        return contractService.getClientContracts();

    }

    @GetMapping("/{contractId}")
    public ApiResponse<ContractResponse> getContractById(
            @PathVariable Long contractId) {

        return contractService.getContractById(contractId);

    }

}