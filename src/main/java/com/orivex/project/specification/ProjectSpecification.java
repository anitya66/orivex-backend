package com.orivex.project.specification;

import org.springframework.data.jpa.domain.Specification;

import com.orivex.project.entity.Project;
import com.orivex.project.enums.ProjectStatus;

public class ProjectSpecification {

    private ProjectSpecification() {
    }

    public static Specification<Project> hasStatus(
            ProjectStatus status) {

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.get("status"),
                status);

    }

    public static Specification<Project> titleContains(
            String keyword) {

        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.lower(root.get("title")),
                "%" + keyword.toLowerCase() + "%");

    }

    public static Specification<Project> hasMinimumBudget(
            Double minBudget) {

        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(
                root.get("budget"),
                minBudget);

    }

}