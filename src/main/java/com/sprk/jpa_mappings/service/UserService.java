package com.sprk.jpa_mappings.service;

import com.sprk.jpa_mappings.dtos.payload.BorrowRequest;
import com.sprk.jpa_mappings.dtos.response.APIResponse;
import com.sprk.jpa_mappings.dtos.payload.UpdateUserRequest;
import com.sprk.jpa_mappings.dtos.payload.UserRequest;
import com.sprk.jpa_mappings.dtos.response.BookResponseV3;
import com.sprk.jpa_mappings.dtos.response.UserResponse;
import com.sprk.jpa_mappings.entities.BookModel;
import com.sprk.jpa_mappings.entities.BorrowingModel;
import com.sprk.jpa_mappings.entities.UserModel;
import com.sprk.jpa_mappings.exceptions.DataNotFoundException;
import com.sprk.jpa_mappings.exceptions.DuplicateDataFoundException;
import com.sprk.jpa_mappings.repository.BookRepository;
import com.sprk.jpa_mappings.repository.BorrowingRepository;
import com.sprk.jpa_mappings.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Collection;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BorrowingRepository borrowingRepository;

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

        UserResponse userResponse = new UserResponse(
                userModel.getId(),
                userModel.getUserName(),
                userModel.getFirstName(),
                userModel.getLastName());

        return APIResponse.builder()
           .data(userResponse)
           .build();
    }

    public APIResponse<?> borrowBook(Long userId, BorrowRequest borrowRequest) {
        // search for user if it exist or not ?
        UserModel userModel = userRepository.findById(userId)
           .orElseThrow(() -> new DataNotFoundException("User with id ("+ userId + ") not found."));

        // search for book if they exist in bookTable, add in bookModel List //////// will skip those which does not exist
        List<BookModel> bookModels = bookRepository.findAllById(borrowRequest.getBookIds());

        // check if (no. of books_borrowing == no. of booksAvailable)
        if (borrowRequest.getBookIds().size() != bookModels.size())
            throw new DataNotFoundException("Provide valid book ids");

        List<BorrowingModel> borrowedModels = borrowingRepository.findByBorrower_IdAndBook_IdIn(userId, borrowRequest.getBookIds());
        if (!borrowedModels.isEmpty()){
            String ids = borrowedModels.stream()
                    .map(br -> {
                        return String.valueOf(br.getBook().getId());
                    })
                    .collect(Collectors.joining(","));
            throw new DuplicateDataFoundException("Books are already borrowed with ids ["+ids+"]");
        }

//
//        Set<BorrowingModel> existingBooks = userModel.getBorrowings();
//        existingBooks.addAll(bookModels);
//        userModel.setBorrowings(existingBooks);
//
//        userRepository.save(userModel);
//
//        userRepository.flush();
        List<BorrowingModel> borrowingModels = new ArrayList<>();
        for (BookModel book : bookModels){
            BorrowingModel borrowingModel = BorrowingModel.builder()
               .borrower(userModel)
               .book(book)
               .borrowDate(Instant.now())
               .build();
            borrowingModels.add(borrowingModel);
        }

        borrowingRepository.saveAll(borrowingModels);


        return APIResponse.builder()
           .message("Book borrowed successfully")
           .build();
    }

    public APIResponse<?> getBorrowedBookByUserId(Long userId) {
        return APIResponse.builder()
           .data(borrowingRepository.getBorrowedBookByUser_Id(userId))
           .build();
    }
//
//    public APIResponse<?> getBooksBorrowedByUserId(Long userId) {
//        // search for user if it exist or not ?
//        UserModel userModel = userRepository.findById(userId)
//                .orElseThrow(() -> new DataNotFoundException("User with id (" + userId + ") not found."));
//
//        List<BorrowingModel> borrowingRecords = borrowingRepository.findByBorrower_Id(userId);
//
//        List<BookResponseV3> borrowedBooks = borrowingRecords
//                .stream()
//                .map(br -> BookResponseV3.builder()
//                        .bookId(br.getBook().getId())
//                        .title(br.getBook().getTitle())
//                        .price(br.getBook().getPrice())
//                        .borrowDate(br.getBorrowDate())
//                        .build())
//                .collect(Collectors.toList());
//        return APIResponse.builder()
//                .data(borrowedBooks)
//                .build();
//    }


}
