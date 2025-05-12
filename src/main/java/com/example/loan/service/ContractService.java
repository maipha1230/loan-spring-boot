package com.example.loan.service;

import com.example.loan.entity.ContractEntity;
import com.example.loan.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;

    public ContractEntity createContract(ContractEntity contract) {
        return  this.contractRepository.save(contract);
    }

    public ContractEntity updateContract(ContractEntity contract) {
        return  this.contractRepository.save(contract);
    }

    public Optional<ContractEntity> getInformation(Long id) {
        return  this.contractRepository.findByIdWithRelations(id);
    }

    public List<ContractEntity> getListContract() {
        return  this.contractRepository.findAllContractRaw();
    }


}
