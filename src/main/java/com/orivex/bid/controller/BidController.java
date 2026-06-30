package com.orivex.bid.controller;

import java.util.List;
import com.orivex.bid.dto.UpdateBidRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.orivex.bid.dto.BidResponse;
import com.orivex.bid.dto.CreateBidRequest;
import com.orivex.bid.service.BidService;
import com.orivex.common.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/bids")
@RequiredArgsConstructor
@Validated
public class BidController {

    private final BidService bidService;

    @PostMapping
    public ApiResponse<BidResponse> createBid(
            @Valid @RequestBody CreateBidRequest request) {

        return bidService.createBid(request);

    }

    @GetMapping("/my")
    public ApiResponse<List<BidResponse>> getMyBids() {

        return bidService.getMyBids();

    }

    @GetMapping("/project/{projectId}")
    public ApiResponse<List<BidResponse>> getProjectBids(
            @PathVariable Long projectId) {

        return bidService.getProjectBids(projectId);

    }

    @PutMapping("/{bidId}")
    public ApiResponse<BidResponse> updateBid(
            @PathVariable Long bidId,
            @Valid @RequestBody UpdateBidRequest request) {

        return bidService.updateBid(
                bidId,
                request);

    }

    @DeleteMapping("/{bidId}")
    public ApiResponse<String> withdrawBid(
            @PathVariable Long bidId) {

        return bidService.withdrawBid(bidId);

    }

    @PutMapping("/{bidId}/accept")
    public ApiResponse<String> acceptBid(
            @PathVariable Long bidId) {

        return bidService.acceptBid(bidId);

    }

    @PutMapping("/{bidId}/reject")
    public ApiResponse<String> rejectBid(
            @PathVariable Long bidId) {

        return bidService.rejectBid(bidId);

    }

}