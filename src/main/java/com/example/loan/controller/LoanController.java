package com.example.loan.controller;

import com.example.loan.dto.LoanRequest;
import com.example.loan.entity.LoanEntity;
import com.example.loan.exception.CustomException;
import com.example.loan.model.BaseResponse;
import com.example.loan.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/loan")
public class LoanController {

    @Autowired
    LoanService loanService;

    @GetMapping("/get-list")
    public ResponseEntity<BaseResponse<List<LoanEntity>>> getList() {
        try {
            List<LoanEntity> loans = loanService.findAll();

            BaseResponse<List<LoanEntity>> successResponse = new BaseResponse<>(HttpStatus.OK.value(), "Get List Loan Success", loans);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof CustomException customException) {
                throw customException; // rethrow with its own status and message
            }
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @GetMapping("/get-information/{id}")
    public ResponseEntity<BaseResponse<LoanEntity>> information(@PathVariable Long id) {
        try {
            Optional<LoanEntity> existLoan = loanService.findById(id);
            if (existLoan.isEmpty()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Loan Not Found");
            }

            BaseResponse<LoanEntity> successResponse = new BaseResponse<>(HttpStatus.OK.value(), "Get Information Loan Success", existLoan.get());
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof CustomException customException) {
                throw customException; // rethrow with its own status and message
            }
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<LoanEntity>> create(@RequestBody @Valid LoanRequest loanRequest) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();

            if (loanRequest.getMinAmount().compareTo(loanRequest.getMaxAmount()) > 0) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Min Amount Must Less Than Max Amount");
            }

            if (loanRequest.getMinRange().compareTo(loanRequest.getMaxRange()) > 0) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Min Range Must Less Than Max Range");
            }

            LoanEntity loan = new LoanEntity();
            loan.setLoanName(loanRequest.getLoanName());
            loan.setMinAmount(loanRequest.getMinAmount());
            loan.setMaxAmount(loanRequest.getMaxAmount());
            loan.setMinRange(loanRequest.getMinRange());
            loan.setMaxRange(loanRequest.getMaxRange());
            loan.setCreatedBy(username);
            loan.setUpdatedBy(username);

            LoanEntity savedLoan = loanService.createLoan(loan);

            BaseResponse<LoanEntity> successResponse = new BaseResponse<>(HttpStatus.OK.value(), "Create loan Success", savedLoan);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof CustomException customException) {
                throw customException; // rethrow with its own status and message
            }
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<BaseResponse<LoanEntity>> update(@RequestBody @Valid LoanRequest loanRequest, @PathVariable Long id) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();

            Optional<LoanEntity> existLoan = loanService.findById(id);
            if (existLoan.isEmpty()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Loan Not Found");
            }

            LoanEntity loan = existLoan.get();
            loan.setLoanName(loanRequest.getLoanName());
            loan.setMinAmount(loanRequest.getMinAmount());
            loan.setMaxAmount(loanRequest.getMaxAmount());
            loan.setMinRange(loanRequest.getMinRange());
            loan.setMaxRange(loanRequest.getMaxRange());
            loan.setUpdatedBy(username);

            LoanEntity savedLoan = loanService.updateLoan(loan);

            BaseResponse<LoanEntity> successResponse = new BaseResponse<>(HttpStatus.OK.value(), "Update loan Success", savedLoan);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof CustomException customException) {
                throw customException; // rethrow with its own status and message
            }
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }


    @PostMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<LoanEntity>> update(@PathVariable Long id) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();

            Optional<LoanEntity> existLoan = loanService.findById(id);
            if (existLoan.isEmpty()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Loan Not Found");
            }

            loanService.deleteById(id);
            BaseResponse<LoanEntity> successResponse = new BaseResponse<>(HttpStatus.OK.value(), "Delete loan Success", null);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof CustomException customException) {
                throw customException; // rethrow with its own status and message
            }
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }


}
