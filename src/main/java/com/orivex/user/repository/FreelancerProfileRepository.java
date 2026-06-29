package com.orivex.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orivex.user.entity.FreelancerProfile;
import com.orivex.user.entity.User;

public interface FreelancerProfileRepository extends JpaRepository<FreelancerProfile, Long> {

    Optional<FreelancerProfile> findByUser(User user);

    boolean existsByUser(User user);

}