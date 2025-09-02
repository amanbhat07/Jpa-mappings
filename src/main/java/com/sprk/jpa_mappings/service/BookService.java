package com.sprk.jpa_mappings.service;

import com.sprk.jpa_mappings.dtos.payload.BookRequest;
import com.sprk.jpa_mappings.dtos.response.APIResponse;
import com.sprk.jpa_mappings.dtos.response.BookResponse;
import com.sprk.jpa_mappings.entities.BookModel;
import com.sprk.jpa_mappings.entities.UserModel;
import com.sprk.jpa_mappings.exceptions.DataNotFoundException;
import com.sprk.jpa_mappings.repository.BookRepository;
import com.sprk.jpa_mappings.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public APIResponse<?> getAllBooks() {
        List<BookResponse> bookResponses = bookRepository.findAll()
           .stream()
           .map(book -> {
               UserModel userModel = book.getAuthor();

               return BookResponse.builder()
                  .bookId(book.getId())
                  .title(book.getTitle())
                  .price(book.getPrice())
                  .author(userModel.getFirstName() + " " + userModel.getLastName())
                  .build();
           })
           .collect(Collectors.toList());

        return APIResponse.builder()
           .data(bookResponses)
           .build();
    }

    public APIResponse<?> addBook(BookRequest bookRequest) {
        UserModel userModel = userRepository.findById(bookRequest.getAuthorId())
           .orElseThrow(() -> new DataNotFoundException("Invalid author ID"));

        BookModel bookModel = BookModel.builder()
           .title(bookRequest.getTitle())
           .price(bookRequest.getPrice())
           .author(userModel)
           .build();

        bookRepository.save(bookModel);

        return APIResponse.builder()
           .message("Book added successfully")
           .build();
    }
}
