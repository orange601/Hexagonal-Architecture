package com.hexagonal.architecture.bank.application.port.out;

import com.hexagonal.architecture.bank.domain.BankAccount;

public interface LoadAccountPort {
	BankAccount load(Long id);

}
