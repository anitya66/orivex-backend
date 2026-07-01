package com.orivex.review.mapper;

import org.springframework.stereotype.Component;

import com.orivex.review.dto.ReviewResponse;
import com.orivex.review.entity.Review;

@Component
public class ReviewMapper {

    public ReviewResponse toResponse(Review review) {

        return ReviewResponse.builder()
                .id(review.getId())
                .contractId(review.getContract().getId())
                .reviewerName(review.getReviewer().getName())
                .revieweeName(review.getReviewee().getName())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .build();

    }

}