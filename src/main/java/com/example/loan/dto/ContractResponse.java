package com.example.loan.dto;

import com.example.loan.entity.CustomerEntity;
import com.example.loan.entity.LoanEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ContractResponse {
    private Long id;
    private BigDecimal totalAmount;
    private BigDecimal rate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long customerId;

    @JsonProperty("Customer")
    private CustomerEntity Customer;

    private Long loanId;

    @JsonProperty("Loan")
    private LoanEntity Loan;
}
