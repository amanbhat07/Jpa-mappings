package com.sprk.jpa_mappings.service;

import com.sprk.jpa_mappings.dtos.payload.BookRequest;
import com.sprk.jpa_mappings.dtos.response.APIResponse;
import com.sprk.jpa_mappings.dtos.response.AuthorBooksResponse;
import com.sprk.jpa_mappings.dtos.response.BookResponse;
import com.sprk.jpa_mappings.dtos.response.BookResponseV2;
import com.sprk.jpa_mappings.entities.BookModel;
import com.sprk.jpa_mappings.entities.BorrowingModel;
import com.sprk.jpa_mappings.entities.UserModel;
import com.sprk.jpa_mappings.exceptions.DataNotFoundException;
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

    public APIResponse<?> getBooksByAuthorId(Long authorId) {
        UserModel author = userRepository.findById(authorId)
           .orElseThrow(() -> new DataNotFoundException("Invalid author ID"));

        AuthorBooksResponse authorBooksResponse = AuthorBooksResponse.builder()
           .authorName(author.getFirstName() + " " + author.getLastName())
           .build();
        List<BookResponseV2> bookResponseV2 = bookRepository.findByAuthor_Id(authorId)
           .stream()
           .map(book -> BookResponseV2.builder()
              .bookId(book.getId())
              .title(book.getTitle())
              .price(book.getPrice())
              .build()
           ).toList();

        authorBooksResponse.setBooks(bookResponseV2);

        return APIResponse.builder()
           .data(authorBooksResponse)
           .build();
    }

    public APIResponse<?> getBorrowingsByBookId(Long bookId) {
        BookModel bookModel = bookRepository.findById(bookId)
           .orElseThrow(() -> new DataNotFoundException("Invalid book ID"));

        Set<BorrowingModel> borrowings = bookModel.getBorrowings();

        List<String> borrowers = borrowings.stream()
           .map(borrowing  -> {
               UserModel borrower = borrowing.getBorrower();
              return borrower.getId() + " " + borrower.getFirstName() + " " + borrower.getLastName();
           })
           .toList();

        return APIResponse.builder()
           .data(borrowers)
           .build();
    }


}
