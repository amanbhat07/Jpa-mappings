package com.sprk.jpa_mappings.dtos.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateUserRequest {

    @NotNull(message = "first name cannot be null")
    @NotBlank(message = "first name cannot be blank")
    @Pattern(
       regexp = "^[a-zA-Z]{3,20}$",
       message = "first name should contain only letters and must be between 3 and 20 characters long"
    )
    private String firstName;

    @NotNull(message = "last name cannot be null")
    @NotBlank(message = "last name cannot be blank")
    @Pattern(
       regexp = "^[a-zA-Z]{3,20}$",
       message = "last name should contain only letters and must be between 3 and 20 characters long"
    )
    private String lastName;
}
