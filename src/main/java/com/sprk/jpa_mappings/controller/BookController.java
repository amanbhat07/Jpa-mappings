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
}
