package com.hexagonal.architecture.bank.application.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.hexagonal.architecture.bank.application.port.in.DepositUseCase;
import com.hexagonal.architecture.bank.application.port.in.WithdrawUseCase;
import com.hexagonal.architecture.bank.application.port.out.LoadAccountPort;
import com.hexagonal.architecture.bank.application.port.out.SaveAccountPort;
import com.hexagonal.architecture.bank.domain.BankAccount;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BankAccountService implements DepositUseCase, WithdrawUseCase {
	
    private final LoadAccountPort loadAccountPort;
    private final SaveAccountPort saveAccountPort;

    @Override
    public void deposit(Long id, BigDecimal amount) {
    	BankAccount account = loadAccountPort.load(id);
    	
    	account.deposit(amount);
    	
    	saveAccountPort.save(account);
    }
    
	@Override
	public boolean withdraw(Long id, BigDecimal amount) {
        BankAccount account = loadAccountPort.load(id);
        
        boolean hasWithdrawn = account.withdraw(amount);
 
        if(hasWithdrawn) {
            saveAccountPort.save(account);
        }
 
        return hasWithdrawn;
	}


}
