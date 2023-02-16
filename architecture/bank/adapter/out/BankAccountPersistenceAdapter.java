package com.hexagonal.architecture.bank.adapter.out;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Repository;

import com.hexagonal.architecture.bank.application.port.out.LoadAccountPort;
import com.hexagonal.architecture.bank.application.port.out.SaveAccountPort;
import com.hexagonal.architecture.bank.domain.BankAccount;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BankAccountPersistenceAdapter implements LoadAccountPort, SaveAccountPort {
	
    private final BankAccountMapper bankAccountMapper;
    private final BankAccountSpringDataRepository repository;
	
	@Override
	public void save(BankAccount bankAccount) {
        BankAccountEntity entity = bankAccountMapper.toEntity(bankAccount);
        repository.save(entity);
	}

	@Override
	public BankAccount load(Long id) {
        BankAccountEntity entity = repository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        
        return bankAccountMapper.toDomain(entity);
	}
	

}
