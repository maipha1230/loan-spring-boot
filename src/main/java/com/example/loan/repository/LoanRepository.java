package com.example.loan.repository;

import com.example.loan.entity.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoanRepository extends JpaRepository<LoanEntity, Long> {

    @Query(value = "SELECT * FROM loans order by id asc", nativeQuery = true)
    List<LoanEntity> findAllLoansRaw();
}
