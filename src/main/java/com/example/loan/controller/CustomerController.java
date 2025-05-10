package com.example.loan.controller;


import com.example.loan.dto.CustomerRequest;
import com.example.loan.entity.CustomerEntity;
import com.example.loan.exception.CustomException;
import com.example.loan.model.BaseResponse;
import com.example.loan.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;


    @GetMapping("/get-list")
    public ResponseEntity<BaseResponse<List<CustomerEntity>>> getList() {
        try {
            List<CustomerEntity> customers = customerService.findAllCustomerRaw();

            BaseResponse<List<CustomerEntity>> successResponse = new BaseResponse<>(HttpStatus.OK.value(), "Get List Customer Success", customers);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof CustomException customException) {
                throw customException; // rethrow with its own status and message
            }
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @GetMapping("/get-information/{id}")
    public ResponseEntity<BaseResponse<CustomerEntity>> information(@PathVariable Long id) {
        try {
            Optional<CustomerEntity> existCustomer = customerService.findById(id);
            if (existCustomer.isEmpty()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Customer Not Found");
            }

            BaseResponse<CustomerEntity> successResponse = new BaseResponse<>(HttpStatus.OK.value(), "Get Information Customer Success", existCustomer.get());
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof CustomException customException) {
                throw customException; // rethrow with its own status and message
            }
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<CustomerEntity>> create(@RequestBody @Valid CustomerRequest customerRequest) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            if (customerService.findByEmail(customerRequest.getEmail()).isPresent()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Customer Email Already Exists");
            }

            if (customerService.findByPhone(customerRequest.getPhone()).isPresent()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Customer Phone Already Exists");
            }

            CustomerEntity customer = new CustomerEntity();
            customer.setPhone(customerRequest.getPhone());
            customer.setEmail(customerRequest.getEmail());
            customer.setFirstname(customerRequest.getFirstname());
            customer.setLastname(customerRequest.getLastname());
            customer.setAddress(customerRequest.getAddress());
            customer.setCreatedBy(username);
            customer.setUpdatedBy(username);

            CustomerEntity savedCustomer = customerService.createCustomer(customer);

            BaseResponse<CustomerEntity> successResponse = new BaseResponse<>(HttpStatus.OK.value(), "Create Customer Success", savedCustomer);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof CustomException customException) {
                throw customException; // rethrow with its own status and message
            }
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<BaseResponse<CustomerEntity>> update(@RequestBody @Valid CustomerRequest customerRequest, @PathVariable Long id) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<CustomerEntity> existCustomer = customerService.findById(id);
            if (existCustomer.isEmpty()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Customer Not Found");
            }

            CustomerEntity customer = existCustomer.get();
            customer.setPhone(customerRequest.getPhone());
            customer.setEmail(customerRequest.getEmail());
            customer.setFirstname(customerRequest.getFirstname());
            customer.setLastname(customerRequest.getLastname());
            customer.setAddress(customerRequest.getAddress());
            customer.setUpdatedBy(username);

            CustomerEntity savedCustomer = customerService.updateCustomer(customer);

            BaseResponse<CustomerEntity> successResponse = new BaseResponse<>(HttpStatus.OK.value(), "Update Customer Success", savedCustomer);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof CustomException customException) {
                throw customException; // rethrow with its own status and message
            }
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<CustomerEntity>> delete(@PathVariable Long id) {
        try {
            Optional<CustomerEntity> existCustomer = customerService.findById(id);
            if (existCustomer.isEmpty()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Customer Not Found");
            }

            customerService.deleteById(id);
            BaseResponse<CustomerEntity> successResponse = new BaseResponse<>(HttpStatus.OK.value(), "Delete Customer Success", null);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof CustomException customException) {
                throw customException; // rethrow with its own status and message
            }
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

}
