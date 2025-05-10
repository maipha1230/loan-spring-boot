package com.example.loan.service;

import com.example.loan.entity.CustomerEntity;
import com.example.loan.entity.UserEntity;
import com.example.loan.repository.CustomerRepository;
import com.example.loan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    // Create User
    public CustomerEntity createCustomer(CustomerEntity user) {
        return customerRepository.save(user);
    }

    // Update User (same as create, just re-save)
    public CustomerEntity updateCustomer(CustomerEntity user) {
        return customerRepository.save(user);
    }

    // Find Customer by ID
    public Optional<CustomerEntity> findById(Long id) {
        return customerRepository.findById(id);
    }

    // Find Customer by Email
    public Optional<CustomerEntity> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    // Find Customer by Phone
    public Optional<CustomerEntity> findByPhone(String phone) {
        return customerRepository.findByPhone(phone);
    }
    // Delete Customer by ID
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

    // find all customer raw
    public List<CustomerEntity> findAllCustomerRaw() {
        return customerRepository.findAllCustomersRaw();
    }
}
