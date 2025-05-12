package com.example.loan.mapper;

import org.mapstruct.*;
import com.example.loan.entity.ContractEntity;
import com.example.loan.dto.ContractResponse;

@Mapper(componentModel = "spring")
public interface ContractMapper {
    ContractResponse toDTO(ContractEntity entity);

}