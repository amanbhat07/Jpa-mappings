package com.sprk.jpa_mappings.controller;

import com.sprk.jpa_mappings.dtos.payload.ProfileRequest;
import com.sprk.jpa_mappings.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
@Validated
public class ProfileController {

    private final ProfileService profileService;



    @GetMapping("/user_id/{user_id}")
    public ResponseEntity<?> getProfile(@PathVariable Long user_id) {
        return ResponseEntity.ok().body(profileService.getProfile(user_id));
    }

    @PostMapping("/user_id/{user_id}")
    public ResponseEntity<?> createProfile(
       @PathVariable Long user_id,
       @RequestBody @Valid ProfileRequest profileRequest) {

        return ResponseEntity
           .status(HttpStatus.CREATED)
           .body(profileService.createProfile(user_id, profileRequest));
    }

}
