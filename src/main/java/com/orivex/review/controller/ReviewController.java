package com.orivex.review.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orivex.common.response.ApiResponse;
import com.orivex.review.dto.CreateReviewRequest;
import com.orivex.review.dto.ReviewResponse;
import com.orivex.review.service.ReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ApiResponse<ReviewResponse> createReview(
            @Valid @RequestBody CreateReviewRequest request) {

        return reviewService.createReview(request);

    }

    @GetMapping("/freelancer/{freelancerId}")
    public ApiResponse<List<ReviewResponse>> getReviewsForFreelancer(
            @PathVariable Long freelancerId) {

        return reviewService.getReviewsForFreelancer(freelancerId);

    }

    @GetMapping("/client/{clientId}")
    public ApiResponse<List<ReviewResponse>> getReviewsForClient(
            @PathVariable Long clientId) {

        return reviewService.getReviewsForClient(clientId);

    }

    @GetMapping("/freelancer/{freelancerId}/average-rating")
    public ApiResponse<Double> getFreelancerAverageRating(
            @PathVariable Long freelancerId) {

        return reviewService.getFreelancerAverageRating(freelancerId);

    }

}