package com.sprk.jpa_mappings.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponseV3 {

    private Long bookId;
    private String title;
    private Double price;
    private Instant borrowDate;
}
