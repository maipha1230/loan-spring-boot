package com.example.loan.repository;

import com.example.loan.entity.ContractEntity;
import com.example.loan.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<ContractEntity, Long> {
    @Query("SELECT c FROM ContractEntity c JOIN FETCH c.customer JOIN FETCH c.loan ORDER BY c.id ASC")
    List<ContractEntity> findAllContractRaw();

    @Query("SELECT c FROM ContractEntity c " +
            "JOIN FETCH c.customer " +
            "JOIN FETCH c.loan " +
            "WHERE c.id = :id")
    Optional<ContractEntity> findByIdWithRelations(Long id);
}
