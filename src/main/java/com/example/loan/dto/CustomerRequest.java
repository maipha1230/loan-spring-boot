package com.example.loan.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CustomerRequest {
    @NotBlank(message = "Phone is required")
    private String phone;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "First Name is required")
    private String firstname;

    @NotBlank(message = "Last Name is required")
    private String lastname;

    private String address;
}
