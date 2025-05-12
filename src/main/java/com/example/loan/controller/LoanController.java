package com.example.loan.controller;

import com.example.loan.dto.ContractRequest;
import com.example.loan.dto.ContractResponse;
import com.example.loan.dto.LoanRequest;
import com.example.loan.entity.ContractEntity;
import com.example.loan.entity.CustomerEntity;
import com.example.loan.entity.LoanEntity;
import com.example.loan.exception.CustomException;
import com.example.loan.mapper.ContractMapper;
import com.example.loan.model.BaseResponse;
import com.example.loan.service.ContractService;
import com.example.loan.service.CustomerService;
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
    private LoanService loanService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ContractMapper contractMapper;

    @GetMapping("/get-list")
    public ResponseEntity<BaseResponse<List<LoanEntity>>> getListLoan() {
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
    public ResponseEntity<BaseResponse<LoanEntity>> informationLoan(@PathVariable Long id) {
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
    public ResponseEntity<BaseResponse<LoanEntity>> createLoan(@RequestBody @Valid LoanRequest loanRequest) {
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
    public ResponseEntity<BaseResponse<LoanEntity>> updateLoan(@RequestBody @Valid LoanRequest loanRequest, @PathVariable Long id) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();

            Optional<LoanEntity> existLoan = loanService.findById(id);
            if (existLoan.isEmpty()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Loan Not Found");
            }

            if (loanRequest.getMinAmount().compareTo(loanRequest.getMaxAmount()) > 0) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Min Amount Must Less Than Max Amount");
            }

            if (loanRequest.getMinRange().compareTo(loanRequest.getMaxRange()) > 0) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Min Range Must Less Than Max Range");
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
    public ResponseEntity<BaseResponse<LoanEntity>> deleteLoan(@PathVariable Long id) {
        try {
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

    @PostMapping("/contract/list")
    public ResponseEntity<BaseResponse<List<ContractResponse>>> getListContract() {
        try {
            List<ContractEntity> contracts = this.contractService.getListContract();
            List<ContractResponse> contractResponses = contracts.stream()
                    .map(this::mapToContractResponse)
                    .toList();

            BaseResponse<List<ContractResponse>> successResponse = new BaseResponse<>(HttpStatus.OK.value(), "Get Contracts Success", contractResponses);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof CustomException customException) {
                throw customException; // rethrow with its own status and message
            }
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @GetMapping("/contract/information/{id}")
    public ResponseEntity<BaseResponse<ContractResponse>> getInformationContract(@PathVariable Long id) {
        try {
            Optional<ContractEntity> exist = this.contractService.getInformation(id);
            if (exist.isEmpty()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Contract Not Found");
            }

            ContractResponse contract = this.mapToContractResponse(exist.get());
            BaseResponse<ContractResponse> successResponse = new BaseResponse<>(HttpStatus.OK.value(), "Get Contract Success", contract);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof CustomException customException) {
                throw customException; // rethrow with its own status and message
            }
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @PostMapping("/contract/create")
    public ResponseEntity<BaseResponse<ContractResponse>> createContract(@RequestBody @Valid ContractRequest contractRequest) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<LoanEntity> existLoan = loanService.findById(contractRequest.getLoanId());
            if (existLoan.isEmpty()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Loan Not Found");
            }

            Optional<CustomerEntity> existCustomer = customerService.findById(contractRequest.getCustomerId());
            if (existCustomer.isEmpty()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Customer Not Found");
            }

            ContractEntity contract = new ContractEntity();
            contract.setLoan(existLoan.get());
            contract.setCustomer(existCustomer.get());
            contract.setRate(contractRequest.getRate());
            contract.setTotalAmount(contractRequest.getTotalAmount());
            contract.setStartDate(contractRequest.getStartDate());
            contract.setEndDate(contractRequest.getEndDate());
            contract.setCreatedBy(username);
            contract.setUpdatedBy(username);

            ContractEntity savedContract = this.contractService.createContract(contract);

            ContractResponse contractResponse = this.mapToContractResponse(savedContract);
            BaseResponse<ContractResponse> successResponse = new BaseResponse<>(HttpStatus.OK.value(), "Get Contracts Success", contractResponse);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof CustomException customException) {
                throw customException; // rethrow with its own status and message
            }
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @PostMapping("/contract/update/{id}")
    public ResponseEntity<BaseResponse<ContractResponse>> updateContract(@RequestBody @Valid ContractRequest contractRequest, @PathVariable Long id) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<ContractEntity> existContract = this.contractService.getInformation(id);
            if (existContract.isEmpty()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Contract Not Found");
            }

            Optional<LoanEntity> existLoan = loanService.findById(contractRequest.getLoanId());
            if (existLoan.isEmpty()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Loan Not Found");
            }

            Optional<CustomerEntity> existCustomer = customerService.findById(contractRequest.getCustomerId());
            if (existCustomer.isEmpty()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Customer Not Found");
            }

            ContractEntity contract = existContract.get();
            contract.setLoan(existLoan.get());
            contract.setCustomer(existCustomer.get());
            contract.setRate(contractRequest.getRate());
            contract.setTotalAmount(contractRequest.getTotalAmount());
            contract.setStartDate(contractRequest.getStartDate());
            contract.setEndDate(contractRequest.getEndDate());
            contract.setUpdatedBy(username);

            ContractEntity savedContract = this.contractService.updateContract(contract);

            ContractResponse contractResponse = this.mapToContractResponse(savedContract);
            BaseResponse<ContractResponse> successResponse = new BaseResponse<>(HttpStatus.OK.value(), "Get Contracts Success", contractResponse);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof CustomException customException) {
                throw customException; // rethrow with its own status and message
            }
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @PostMapping("/contract/cancel/{id}")
    public ResponseEntity<BaseResponse<ContractResponse>> cancelContract(@PathVariable Long id) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<ContractEntity> existContract = this.contractService.getInformation(id);
            if (existContract.isEmpty()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Contract Not Found");
            }

            ContractEntity contract = existContract.get();
            contract.setStatus("CANCEL");
            contract.setUpdatedBy(username);

            ContractEntity savedContract = this.contractService.updateContract(contract);

            ContractResponse contractResponse = this.mapToContractResponse(savedContract);
            BaseResponse<ContractResponse> successResponse = new BaseResponse<>(HttpStatus.OK.value(), "Get Contracts Success", contractResponse);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof CustomException customException) {
                throw customException; // rethrow with its own status and message
            }
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    private ContractResponse mapToContractResponse(ContractEntity entity) {
        ContractResponse response = new ContractResponse();
        response.setId(entity.getId());
        response.setTotalAmount(entity.getTotalAmount());
        response.setRate(entity.getRate());
        response.setStartDate(entity.getStartDate());
        response.setEndDate(entity.getEndDate());
        response.setStatus(entity.getStatus());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());

        // Set nested IDs
        if (entity.getCustomer() != null) {
            response.setCustomerId(entity.getCustomer().getId());
            response.setCustomer(entity.getCustomer());
        }

        if (entity.getLoan() != null) {
            response.setLoanId(entity.getLoan().getId());
            response.setLoan(entity.getLoan());
        }

        return response;
    }


}
