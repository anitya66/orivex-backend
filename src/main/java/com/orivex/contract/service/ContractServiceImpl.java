package com.orivex.contract.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.orivex.bid.entity.Bid;
import com.orivex.common.exception.BadRequestException;
import com.orivex.common.response.ApiResponse;
import com.orivex.contract.dto.ContractResponse;
import com.orivex.contract.entity.Contract;
import com.orivex.contract.enums.ContractStatus;
import com.orivex.contract.mapper.ContractMapper;
import com.orivex.contract.repository.ContractRepository;
import com.orivex.security.AuthenticationFacade;
import com.orivex.user.entity.ClientProfile;
import com.orivex.user.entity.FreelancerProfile;
import com.orivex.user.entity.User;
import com.orivex.user.repository.ClientProfileRepository;
import com.orivex.user.repository.FreelancerProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;

    private final FreelancerProfileRepository freelancerProfileRepository;

    private final ClientProfileRepository clientProfileRepository;

    private final AuthenticationFacade authenticationFacade;

    private final ContractMapper contractMapper;

    @Override
    public ApiResponse<List<ContractResponse>> getMyContracts() {

        User currentUser = authenticationFacade.getCurrentUser();

        FreelancerProfile freelancer = freelancerProfileRepository
                .findByUser(currentUser)
                .orElseThrow(() -> new BadRequestException(
                        "Freelancer profile not found."));

        List<ContractResponse> response = contractRepository
                .findByFreelancer(freelancer)
                .stream()
                .map(contractMapper::toResponse)
                .collect(Collectors.toList());

        return ApiResponse.success(
                response,
                "Contracts fetched successfully.");

    }

    @Override
    public ApiResponse<List<ContractResponse>> getClientContracts() {

        User currentUser = authenticationFacade.getCurrentUser();

        ClientProfile client = clientProfileRepository
                .findByUser(currentUser)
                .orElseThrow(() -> new BadRequestException(
                        "Client profile not found."));

        List<ContractResponse> response = contractRepository
                .findByClient(client)
                .stream()
                .map(contractMapper::toResponse)
                .collect(Collectors.toList());

        return ApiResponse.success(
                response,
                "Contracts fetched successfully.");

    }

    @Override
    public ApiResponse<ContractResponse> getContractById(
            Long contractId) {

        User currentUser = authenticationFacade.getCurrentUser();

        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new BadRequestException(
                        "Contract not found."));

        boolean isClient = contract.getClient()
                .getUser()
                .getId()
                .equals(currentUser.getId());

        boolean isFreelancer = contract.getFreelancer()
                .getUser()
                .getId()
                .equals(currentUser.getId());

        if (!isClient && !isFreelancer) {

            throw new BadRequestException(
                    "You are not authorized to view this contract.");

        }

        ContractResponse response = contractMapper.toResponse(contract);

        return ApiResponse.success(
                response,
                "Contract fetched successfully.");

    }

    @Override
    public void createContract(Bid acceptedBid) {

        if (contractRepository.existsByBid(acceptedBid)) {

            throw new BadRequestException(
                    "Contract already exists for this bid.");

        }

        Contract contract = Contract.builder()
                .project(acceptedBid.getProject())
                .client(acceptedBid.getProject().getClient())
                .freelancer(acceptedBid.getFreelancer())
                .bid(acceptedBid)
                .agreedBudget(acceptedBid.getProposedBudget())
                .deadline(acceptedBid.getProject().getDeadline())
                .status(ContractStatus.PENDING)
                .build();

        contractRepository.save(contract);

    }

}