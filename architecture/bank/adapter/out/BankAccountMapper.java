package com.hexagonal.architecture.bank.adapter.out;

import org.springframework.stereotype.Component;

import com.hexagonal.architecture.bank.domain.BankAccount;

@Component
public class BankAccountMapper {
    public BankAccount toDomain(BankAccountEntity entity) {
        return BankAccount.builder()
                .id(entity.getId())
                .balance(entity.getBalance())
                .build();
    }

    public BankAccountEntity toEntity(BankAccount domain) {
        return BankAccountEntity.builder()
                .balance(domain.getBalance())
                .build();
    }

}
