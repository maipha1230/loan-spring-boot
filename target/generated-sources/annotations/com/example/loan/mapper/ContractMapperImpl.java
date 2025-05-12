package com.example.loan.mapper;

import com.example.loan.dto.ContractResponse;
import com.example.loan.entity.ContractEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2568-05-12T17:46:03+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.2 (Homebrew)"
)
@Component
public class ContractMapperImpl implements ContractMapper {

    @Override
    public ContractResponse toDTO(ContractEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ContractResponse contractResponse = new ContractResponse();

        return contractResponse;
    }
}
