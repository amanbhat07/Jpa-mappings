package com.sprk.jpa_mappings.service;

import com.sprk.jpa_mappings.dtos.payload.BorrowRequest;
import com.sprk.jpa_mappings.dtos.response.APIResponse;
import com.sprk.jpa_mappings.dtos.payload.UpdateUserRequest;
import com.sprk.jpa_mappings.dtos.payload.UserRequest;
import com.sprk.jpa_mappings.dtos.response.UserResponse;
import com.sprk.jpa_mappings.entities.BookModel;
import com.sprk.jpa_mappings.entities.UserModel;
import com.sprk.jpa_mappings.exceptions.DataNotFoundException;
import com.sprk.jpa_mappings.exceptions.DuplicateDataFoundException;
import com.sprk.jpa_mappings.repository.BookRepository;
import com.sprk.jpa_mappings.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public APIResponse<?> getAllUsers() {
//        List<UserResponse> userResponseList = userRepository.findAll()
//           .stream()
//           .map(user -> UserResponse.builder()
//              .userId(user.getId())
//              .userName(user.getUserName())
//              .firstName(user.getFirstName())
//              .lastName(user.getLastName())
//              .build()
//           ).collect(Collectors.toList());

        return APIResponse.builder()
           .data(userRepository.findAllProjectedBy())
           .build();
    }

    public APIResponse<?> createUser(UserRequest userRequest) {

        if(userRepository.existsByUserName(userRequest.getUserName()))
            throw new DuplicateDataFoundException("Username already exists. Please choose a different username.");

        UserModel userModel = UserModel.builder()
           .userName(userRequest.getUserName())
           .firstName(userRequest.getFirstName())
           .lastName(userRequest.getLastName())
           .build();

        userModel = userRepository.save(userModel);

        return APIResponse.builder()
           .data(userModel)
           .message("User created successfully")
           .build();
    }

    public APIResponse<?> updateUser(Long id, UpdateUserRequest userRequest) {
        UserModel userModel = userRepository.findById(id)
           .orElseThrow(() -> new DataNotFoundException("User with id ("+ id + ") not found."));

        userModel.setFirstName(userRequest.getFirstName());
        userModel.setLastName(userRequest.getLastName());

        userRepository.save(userModel);
        return APIResponse.builder()
           .message("User updated successfully")
           .build();
    }

    public APIResponse<?> deleteUser(Long id) {
        UserModel userModel = userRepository.findById(id)
           .orElseThrow(() -> new DataNotFoundException("User with id ("+ id + ") not found."));

        userRepository.delete(userModel);
        return APIResponse.builder()
           .message("User deleted successfully")
           .build();
    }

    public APIResponse<?> getUserById(Long id) {
        UserModel userModel = userRepository.findById(id)
           .orElseThrow(() -> new DataNotFoundException("User with id ("+ id + ") not found."));

        return APIResponse.builder()
           .data(userModel)
           .build();
    }

    public APIResponse<?> borrowBook(Long userId, BorrowRequest borrowRequest) {
        UserModel userModel = userRepository.findById(userId)
           .orElseThrow(() -> new DataNotFoundException("User with id ("+ userId + ") not found."));

        List<BookModel> bookModels = bookRepository.findAllById(borrowRequest.getBookIds());
        if (borrowRequest.getBookIds().size() != bookModels.size())
            throw new DataNotFoundException("Provide valid book ids");

        Set<BookModel> existingBooks = userModel.getBorrowings();
        existingBooks.addAll(bookModels);
        userModel.setBorrowings(existingBooks);

        userRepository.save(userModel);

        userRepository.flush();

        return APIResponse.builder()
           .message("Book borrowed successfully")
           .build();
    }
}
