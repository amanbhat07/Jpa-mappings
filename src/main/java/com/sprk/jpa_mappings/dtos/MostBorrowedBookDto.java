package com.sprk.jpa_mappings.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class MostBorrowedBookDto {
    private Long bookId;
    private String title;
    private Long borrowCount;
}
