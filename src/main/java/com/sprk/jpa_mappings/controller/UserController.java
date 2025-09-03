package com.sprk.jpa_mappings.controller;

import com.sprk.jpa_mappings.dtos.payload.BorrowRequest;
import com.sprk.jpa_mappings.dtos.response.APIResponse;
import com.sprk.jpa_mappings.dtos.payload.UpdateUserRequest;
import com.sprk.jpa_mappings.dtos.payload.UserRequest;
import com.sprk.jpa_mappings.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Validated
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity
           .status(HttpStatus.OK)
           .body(userService.getAllUsers());
    }

    @PostMapping("/")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserRequest userRequest) {
        APIResponse<?> response = userService.createUser(userRequest);
        return ResponseEntity
           .status(HttpStatus.CREATED)
           .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return ResponseEntity
           .status(HttpStatus.OK)
           .body(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserRequest userRequest) {
        return ResponseEntity
           .status(HttpStatus.OK)
           .body(userService.updateUser(id, userRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return ResponseEntity
           .status(HttpStatus.OK)
           .body(userService.deleteUser(id));
    }


    @PostMapping("/borrow/{userId}")
    public ResponseEntity<?> borrowBook(@PathVariable Long userId, @RequestBody @Valid BorrowRequest borrowRequest) {
        return ResponseEntity
           .status(HttpStatus.OK)
           .body(userService.borrowBook(userId, borrowRequest));
    }

    @GetMapping("/borrowed/{userId}")
    public ResponseEntity<?> getBorrowedBookByUserId(@PathVariable Long userId){
        return ResponseEntity
           .status(HttpStatus.OK)
           .body(userService.getBorrowedBookByUserId(userId));
    }


}
