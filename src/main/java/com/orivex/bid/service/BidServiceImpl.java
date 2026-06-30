package com.orivex.bid.service;

import com.orivex.contract.service.ContractService;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.orivex.bid.dto.BidResponse;
import com.orivex.bid.dto.CreateBidRequest;
import com.orivex.bid.dto.UpdateBidRequest;
import com.orivex.bid.entity.Bid;
import com.orivex.bid.enums.BidStatus;
import com.orivex.bid.mapper.BidMapper;
import com.orivex.bid.repository.BidRepository;
import com.orivex.common.exception.BadRequestException;
import com.orivex.common.response.ApiResponse;
import com.orivex.project.entity.Project;
import com.orivex.project.enums.ProjectStatus;
import com.orivex.project.repository.ProjectRepository;
import com.orivex.security.AuthenticationFacade;
import com.orivex.user.entity.FreelancerProfile;
import com.orivex.user.entity.User;
import com.orivex.user.repository.FreelancerProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;

    private final BidMapper bidMapper;

    private final ProjectRepository projectRepository;

    private final FreelancerProfileRepository freelancerProfileRepository;

    private final AuthenticationFacade authenticationFacade;


    private final ContractService contractService;

    @Override
    public ApiResponse<BidResponse> createBid(
            CreateBidRequest request) {

        User currentUser = authenticationFacade.getCurrentUser();

        FreelancerProfile freelancer = freelancerProfileRepository
                .findByUser(currentUser)
                .orElseThrow(() -> new BadRequestException(
                        "Freelancer profile not found."));

        Project project = projectRepository
                .findById(request.getProjectId())
                .orElseThrow(() -> new BadRequestException(
                        "Project not found."));

        if (project.getStatus() != ProjectStatus.OPEN) {

            throw new BadRequestException(
                    "Project is not open for bidding.");

        }

        if (project.getClient()
                .getUser()
                .getId()
                .equals(currentUser.getId())) {

            throw new BadRequestException(
                    "You cannot bid on your own project.");

        }

        if (bidRepository.existsByProjectAndFreelancer(
                project,
                freelancer)) {

            throw new BadRequestException(
                    "You have already placed a bid on this project.");

        }

        Bid bid = bidMapper.toEntity(request);

        bid.setProject(project);

        bid.setFreelancer(freelancer);

        bid.setStatus(BidStatus.PENDING);

        Bid savedBid = bidRepository.save(bid);

        return ApiResponse.success(
                bidMapper.toResponse(savedBid),
                "Bid submitted successfully.");

    }

    @Override
    public ApiResponse<List<BidResponse>> getMyBids() {

        User currentUser = authenticationFacade.getCurrentUser();

        FreelancerProfile freelancer = freelancerProfileRepository
                .findByUser(currentUser)
                .orElseThrow(() -> new BadRequestException(
                        "Freelancer profile not found."));

        List<BidResponse> response = bidRepository
                .findByFreelancer(freelancer)
                .stream()
                .map(bidMapper::toResponse)
                .toList();

        return ApiResponse.success(
                response,
                "Bids fetched successfully.");

    }

    @Override
    public ApiResponse<List<BidResponse>> getProjectBids(
            Long projectId) {

        Project project = projectRepository
                .findById(projectId)
                .orElseThrow(() -> new BadRequestException(
                        "Project not found."));

        List<BidResponse> response = bidRepository
                .findByProject(project)
                .stream()
                .map(bidMapper::toResponse)
                .toList();

        return ApiResponse.success(
                response,
                "Project bids fetched successfully.");

    }

    @Override
    public ApiResponse<BidResponse> updateBid(
            Long bidId,
            UpdateBidRequest request) {

        User currentUser = authenticationFacade.getCurrentUser();

        FreelancerProfile freelancer = freelancerProfileRepository
                .findByUser(currentUser)
                .orElseThrow(() -> new BadRequestException(
                        "Freelancer profile not found."));

        Bid bid = bidRepository
                .findById(bidId)
                .orElseThrow(() -> new BadRequestException(
                        "Bid not found."));

        if (!bid.getFreelancer()
                .getId()
                .equals(freelancer.getId())) {

            throw new BadRequestException(
                    "You can update only your own bid.");

        }

        if (bid.getStatus() != BidStatus.PENDING) {

            throw new BadRequestException(
                    "Only pending bids can be updated.");

        }

        bidMapper.updateEntity(
                request,
                bid);

        Bid updatedBid = bidRepository.save(bid);

        return ApiResponse.success(
                bidMapper.toResponse(updatedBid),
                "Bid updated successfully.");

    }

    @Override
    public ApiResponse<String> withdrawBid(
            Long bidId) {

        User currentUser = authenticationFacade.getCurrentUser();

        FreelancerProfile freelancer = freelancerProfileRepository
                .findByUser(currentUser)
                .orElseThrow(() -> new BadRequestException(
                        "Freelancer profile not found."));

        Bid bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new BadRequestException(
                        "Bid not found."));

        if (!bid.getFreelancer().getId().equals(freelancer.getId())) {

            throw new BadRequestException(
                    "You can withdraw only your own bid.");

        }

        if (bid.getStatus() != BidStatus.PENDING) {

            throw new BadRequestException(
                    "Only pending bids can be withdrawn.");

        }

        bid.setStatus(BidStatus.WITHDRAWN);

        bidRepository.save(bid);

        return ApiResponse.success(
                "Bid withdrawn successfully.");

    }

    @Transactional
    @Override
    public ApiResponse<String> acceptBid(Long bidId) {

        User currentUser = authenticationFacade.getCurrentUser();

        Bid acceptedBid = bidRepository.findById(bidId)
                .orElseThrow(() -> new BadRequestException(
                        "Bid not found."));

        if (!acceptedBid.getProject()
                .getClient()
                .getUser()
                .getId()
                .equals(currentUser.getId())) {

            throw new BadRequestException(
                    "You can accept bids only for your own projects.");

        }

        if (acceptedBid.getStatus() != BidStatus.PENDING) {

            throw new BadRequestException(
                    "Only pending bids can be accepted.");

        }

        // Accept selected bid
        acceptedBid.setStatus(BidStatus.ACCEPTED);

        bidRepository.save(acceptedBid);

        contractService.createContract(acceptedBid);

        // Update Project Status
        Project project = acceptedBid.getProject();

        project.setStatus(ProjectStatus.IN_PROGRESS);

        projectRepository.save(project);

        // Reject all remaining pending bids
        List<Bid> otherPendingBids = bidRepository.findByProjectAndStatus(
                project,
                BidStatus.PENDING);

        for (Bid bid : otherPendingBids) {

            if (!bid.getId().equals(acceptedBid.getId())) {

                bid.setStatus(BidStatus.REJECTED);

                bidRepository.save(bid);

            }

        }

        return ApiResponse.success(
                "Bid accepted successfully.");

    }

    @Override
    public ApiResponse<String> rejectBid(
        Long bidId) {

    User currentUser = authenticationFacade.getCurrentUser();

    Bid bid = bidRepository.findById(bidId)
            .orElseThrow(() ->
                    new BadRequestException(
                            "Bid not found."));

    if (!bid.getProject()
            .getClient()
            .getUser()
            .getId()
            .equals(currentUser.getId())) {

        throw new BadRequestException(
                "You can reject bids only for your own projects.");

    }

    if (bid.getStatus() != BidStatus.PENDING) {

        throw new BadRequestException(
                "Only pending bids can be rejected.");

    }

    bid.setStatus(BidStatus.REJECTED);

    bidRepository.save(bid);

    return ApiResponse.success(
            "Bid rejected successfully.");

   }

}