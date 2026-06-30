package com.orivex.bid.entity;

import java.math.BigDecimal;

import com.orivex.bid.enums.BidStatus;
import com.orivex.common.entity.BaseEntity;
import com.orivex.project.entity.Project;
import com.orivex.user.entity.FreelancerProfile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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
@Table(name = "bids")
public class Bid extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "freelancer_profile_id", nullable = false)
    private FreelancerProfile freelancer;

    @Column(nullable = false, length = 2000)
    private String coverLetter;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal proposedBudget;

    @Column(nullable = false)
    private Integer estimatedDays;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BidStatus status;

}