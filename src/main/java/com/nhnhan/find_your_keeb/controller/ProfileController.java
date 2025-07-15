package com.nhnhan.find_your_keeb.controller;

import com.nhnhan.find_your_keeb.dto.UserProfileResponse;
import com.nhnhan.find_your_keeb.dto.UserProfileUpdateRequest;
import com.nhnhan.find_your_keeb.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final AuthService authService;

    @Autowired
    public ProfileController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public UserProfileResponse getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return authService.getUserProfile(userDetails.getUsername());
    }

    @PutMapping
    public UserProfileResponse updateProfile(@AuthenticationPrincipal UserDetails userDetails,
                                             @RequestBody UserProfileUpdateRequest request) {
        return authService.updateUserProfile(userDetails.getUsername(), request);
    }
} 