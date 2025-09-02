package com.sprk.jpa_mappings.dtos.payload;

import lombok.Data;

import java.util.Set;

@Data
public class BorrowRequest {
    private Set<Long> bookIds;
}
