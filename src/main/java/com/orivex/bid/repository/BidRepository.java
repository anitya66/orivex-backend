package com.orivex.bid.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orivex.bid.entity.Bid;
import com.orivex.project.entity.Project;
import com.orivex.user.entity.FreelancerProfile;

public interface BidRepository extends JpaRepository<Bid, Long> {

    List<Bid> findByFreelancer(FreelancerProfile freelancer);

    List<Bid> findByProject(Project project);

    boolean existsByProjectAndFreelancer(
            Project project,
            FreelancerProfile freelancer);

}