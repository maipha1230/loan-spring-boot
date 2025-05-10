package com.example.loan.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class LoanRequest {
    @NotBlank(message = "Loan Name is required")
    private String loanName;

    @NotNull(message = "Min Amount is required")
    private BigDecimal minAmount;

    @NotNull(message = "Max Amount is required")
    private BigDecimal maxAmount;

    @NotNull(message = "Min Range is required")
    private Integer minRange;

    @NotNull(message = "Max Range is required")
    private Integer maxRange;

}
