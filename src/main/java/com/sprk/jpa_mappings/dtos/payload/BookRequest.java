package com.sprk.jpa_mappings.dtos.payload;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class BookRequest {

    @NotBlank(message = "Title is required")
    @Pattern(regexp = "^[a-zA-Z0-9 ]{3,50}$", message = "Title should contain only alphabets, numbers and spaces and should be between 3 to 50 characters long.")
    private String title;

    @NotNull(message = "Price is required")
    @Digits(integer = 3, fraction = 2, message = "Price should be between 0 to 999.99")
    @PositiveOrZero(message = "Price should be a positive number")
    private Double price;

    @NotNull(message = "Author ID is required")
    @Positive(message = "Author ID should be a positive number")
    private Long authorId;
}
