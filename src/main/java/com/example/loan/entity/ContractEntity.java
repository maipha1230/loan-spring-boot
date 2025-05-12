package com.example.loan.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "contracts")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "total_amount")
    private BigDecimal totalAmount;

    @Column(nullable = false, name = "rate")
    private BigDecimal rate;

    @Column(nullable = false, name = "start_date")
    private LocalDateTime startDate;

    @Column(nullable = false, name = "end_date")
    private LocalDateTime endDate;

    //Foreign Keys
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id", nullable = false)
    private LoanEntity loan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "status")
    private String status;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = "ACTIVE";
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
