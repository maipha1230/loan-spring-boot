package com.example.loan.repository;

import com.example.loan.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByPhone(String phone);
    Optional<CustomerEntity> findByEmail(String email);


    @Query(value = "SELECT * FROM customers order by id asc", nativeQuery = true)
    List<CustomerEntity> findAllCustomersRaw();
}
