package com.sprk.jpa_mappings.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponseV2 {
    private Long bookId;
    private String title;
    private Double price;
}
