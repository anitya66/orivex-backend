package com.orivex.contract.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.orivex.bid.entity.Bid;
import com.orivex.common.entity.BaseEntity;
import com.orivex.contract.enums.ContractStatus;
import com.orivex.project.entity.Project;
import com.orivex.user.entity.ClientProfile;
import com.orivex.user.entity.FreelancerProfile;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contracts")
public class Contract extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_profile_id", nullable = false)
    private ClientProfile client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "freelancer_profile_id", nullable = false)
    private FreelancerProfile freelancer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bid_id", nullable = false, unique = true)
    private Bid bid;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal agreedBudget;

    @Column(nullable = false)
    private LocalDate deadline;

    @Column(length = 500)
    private String submissionUrl;

    @Column(length = 1000)
    private String submissionNotes;

    private LocalDate submittedAt;

    private LocalDate startedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContractStatus status;

}