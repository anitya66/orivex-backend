package com.orivex.contract.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orivex.bid.entity.Bid;
import com.orivex.contract.entity.Contract;
import com.orivex.user.entity.ClientProfile;
import com.orivex.user.entity.FreelancerProfile;

public interface ContractRepository
        extends JpaRepository<Contract, Long> {

    List<Contract> findByFreelancer(
            FreelancerProfile freelancer);

    List<Contract> findByClient(
            ClientProfile client);

    boolean existsByBid(
            Bid bid);

}