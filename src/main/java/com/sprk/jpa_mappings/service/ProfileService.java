package com.sprk.jpa_mappings.service;

import com.sprk.jpa_mappings.dtos.payload.ProfileRequest;
import com.sprk.jpa_mappings.dtos.response.APIResponse;
import com.sprk.jpa_mappings.dtos.response.ProfileResponse;
import com.sprk.jpa_mappings.entities.ProfileModel;
import com.sprk.jpa_mappings.entities.UserModel;
import com.sprk.jpa_mappings.exceptions.DataNotFoundException;
import com.sprk.jpa_mappings.exceptions.DuplicateDataFoundException;
import com.sprk.jpa_mappings.repository.ProfileRepository;
import com.sprk.jpa_mappings.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public APIResponse getProfile(Long userId) {
//        UserModel userModel = userRepository.findById(userId)
//           .orElseThrow(() -> new DataNotFoundException("Invalid User ID"));
//
//        ProfileModel profileModel = profileRepository.findByUser(userModel)
//           .orElseThrow(() -> new DataNotFoundException("Profile not found for user ID: " + userId));

        ProfileResponse profileResponse = profileRepository.findByUserUserId(userId)
           .stream()
           .map(profile -> {

               UserModel user = profile.getUser();
               String fullName = user.getFirstName() + " " + user.getLastName();

               return ProfileResponse.builder()
                  .user_id(user.getId())
                  .userName(user.getUserName())
                  .fullName(fullName)
                  .email(profile.getEmail())
                  .phoneNumber(profile.getPhone())
                  .build();
           })
           .findFirst()
           .orElseThrow(() -> new DataNotFoundException("Profile not found for user ID: " + userId));

        return APIResponse.builder()
           .data(profileResponse)
           .build();
    }

    public APIResponse<?> createProfile(Long userId, @Valid ProfileRequest profileRequest) {
        UserModel userModel = userRepository.findById(userId)
           .orElseThrow(() -> new DataNotFoundException("Invalid User ID"));

        if(profileRepository.existsByUserId(userId))
            throw new DuplicateDataFoundException("Profile already exists for user ID: " + userId);

        ProfileModel profileModel = ProfileModel.builder()
           .email(profileRequest.getEmail())
           .phone(profileRequest.getPhone())
           .user(userModel)
           .build();

        profileRepository.save(profileModel);

        return APIResponse.builder()
           .message("Profile created successfully")
           .build();
    }
}
