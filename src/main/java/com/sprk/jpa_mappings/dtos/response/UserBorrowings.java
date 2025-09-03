package com.sprk.jpa_mappings.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@Builder
public class UserBorrowings {
    private Long bookId;
    private String title;
    private Double price;
    private Instant borrowedDate;

    public UserBorrowings(Long bookId, String title, Double price, Instant borrowedDate) {
        this.bookId = bookId;
        this.title = title;
        this.price = price;
        this.borrowedDate = borrowedDate;
    }
}
