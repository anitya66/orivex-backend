package com.orivex.project.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.orivex.project.enums.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import com.orivex.project.entity.Project;

import com.orivex.user.entity.ClientProfile;

public interface ProjectRepository
        extends JpaRepository<Project, Long>,
        JpaSpecificationExecutor<Project> {

    List<Project> findByClient(ClientProfile client);

    List<Project> findByStatus(ProjectStatus status);

    List<Project> findByClientAndStatus(
            ClientProfile client,
            ProjectStatus status);

    Page<Project> findByStatus(
            ProjectStatus status,
            Pageable pageable);

    Page<Project> findByTitleContainingIgnoreCase(
            String keyword,
            Pageable pageable);        

        }