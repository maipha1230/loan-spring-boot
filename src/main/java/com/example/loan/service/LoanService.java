package com.example.loan.service;

import com.example.loan.entity.LoanEntity;
import com.example.loan.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;

    // Create Loan
    public LoanEntity createLoan(LoanEntity user) {
        return loanRepository.save(user);
    }

    // Update Loan (same as create, just re-save)
    public LoanEntity updateLoan(LoanEntity user) {
        return loanRepository.save(user);
    }

    // Find loan by ID
    public Optional<LoanEntity> findById(Long id) {
        return loanRepository.findById(id);
    }

    // Delete loan by ID
    public void deleteById(Long id) {
        loanRepository.deleteById(id);
    }

    // find all loan raw
    public List<LoanEntity> findAll() {
        return loanRepository.findAllLoansRaw();
    }
}
