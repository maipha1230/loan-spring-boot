package com.example.loan.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class ContractRequest {

    @NotNull(message = "Loan Id is required")
    private Long loanId;

    @NotNull(message = "Customer Id is required")
    private Long customerId;

    @NotNull(message = "Rate is required")
    private BigDecimal rate;

    @NotNull(message = "Total Amount is required")
    private BigDecimal totalAmount;

    @NotNull(message = "Start Date is required")
    private LocalDateTime startDate;

    @NotNull(message = "End Date is required")
    private LocalDateTime endDate;

}
