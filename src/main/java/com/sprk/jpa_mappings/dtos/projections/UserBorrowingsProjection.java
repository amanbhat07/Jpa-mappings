package com.sprk.jpa_mappings.dtos.projections;

import java.time.Instant;

public interface UserBorrowingsProjection {
    Long getBookId();
    String getTitle();
    Double getPrice();
    Instant getBorrowedDate();
}
