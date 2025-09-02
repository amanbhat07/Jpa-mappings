package com.sprk.jpa_mappings.dtos.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ProfileRequest {
    @NotBlank(message = "email cannot be blank")
    @Email(message = "email should be valid")
    private String email;

    @NotBlank(message = "phone cannot be blank")
    @Pattern(regexp = "^\\d{10,15}$", message = "phone should be valid")
    private String phone;
}
