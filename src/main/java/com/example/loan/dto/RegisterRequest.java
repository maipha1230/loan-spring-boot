package com.example.loan.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class RegisterRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "Role is required")
    private String role; // Enum type
}
