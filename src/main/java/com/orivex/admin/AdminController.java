package com.orivex.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @GetMapping("/api/v1/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String dashboard() {

        return "Welcome Admin";

    }

}