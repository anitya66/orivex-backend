package com.orivex.review.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;


import com.orivex.contract.entity.Contract;
import com.orivex.review.entity.Review;
import com.orivex.user.entity.User;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByReviewee(User reviewee);

    boolean existsByContractAndReviewer(
            Contract contract,
            User reviewer);

    @Query("""
            SELECT AVG(r.rating)
            FROM Review r
            WHERE r.reviewee = :reviewee
            """)
    Double findAverageRatingByReviewee(
            @Param("reviewee") User reviewee);       

}