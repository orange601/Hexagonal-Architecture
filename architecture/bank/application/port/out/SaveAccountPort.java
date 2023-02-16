package com.hexagonal.architecture.bank.application.port.out;

import com.hexagonal.architecture.bank.domain.BankAccount;

public interface SaveAccountPort {
	void save(BankAccount bankAccount);

}
