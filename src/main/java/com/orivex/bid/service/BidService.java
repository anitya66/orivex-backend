package com.orivex.bid.service;

import java.util.List;

import com.orivex.bid.dto.BidResponse;
import com.orivex.bid.dto.CreateBidRequest;
import com.orivex.bid.dto.UpdateBidRequest;
import com.orivex.common.response.ApiResponse;

public interface BidService {

    ApiResponse<BidResponse> createBid(
            CreateBidRequest request);

    ApiResponse<List<BidResponse>> getMyBids();

    ApiResponse<List<BidResponse>> getProjectBids(
            Long projectId);

    ApiResponse<BidResponse> updateBid(
            Long bidId,
            UpdateBidRequest request);

    ApiResponse<String> withdrawBid(
            Long bidId);

    ApiResponse<String> acceptBid(
            Long bidId);

    ApiResponse<String> rejectBid(
            Long bidId);       

}