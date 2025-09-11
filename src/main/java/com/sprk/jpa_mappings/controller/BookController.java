package com.sprk.jpa_mappings.controller;

import com.sprk.jpa_mappings.dtos.payload.BookRequest;
import com.sprk.jpa_mappings.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
@Validated
public class BookController {

    private final BookService bookService;

    @GetMapping("/")
    public ResponseEntity<?> getAllBooks() {
        return ResponseEntity
           .status(HttpStatus.OK)
           .body(bookService.getAllBooks());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestBody @Valid BookRequest bookRequest) {
        return ResponseEntity
           .status(HttpStatus.CREATED)
           .body(bookService.addBook(bookRequest));
    }

    @GetMapping("/authorId/{authorId}")
    public ResponseEntity<?> getBooksByAuthorId(@PathVariable Long authorId){
        return ResponseEntity
           .status(HttpStatus.OK)
           .body(bookService.getBooksByAuthorId(authorId));
    }

    @GetMapping("/borrowers/{bookId}")
    public ResponseEntity<?> getBorrowingsByBookId(@PathVariable Long bookId){
        return ResponseEntity
           .status(HttpStatus.OK)
           .body(bookService.getBorrowingsByBookId(bookId));
    }

    //get book by book id
    @GetMapping("/book/{bookId}")
    public ResponseEntity<?> getBookByBookId(@PathVariable Long bookId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookService.getBookByBookId(bookId));
    }

    @GetMapping("/max")
    public ResponseEntity<?> getMaxBorrowedBook(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookService.getMaxBorrowedBook());
    }
}
