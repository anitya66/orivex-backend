package com.orivex.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orivex.user.entity.ClientProfile;
import com.orivex.user.entity.User;

public interface ClientProfileRepository
        extends JpaRepository<ClientProfile, Long> {

    Optional<ClientProfile> findByUser(User user);

    boolean existsByUser(User user);

}