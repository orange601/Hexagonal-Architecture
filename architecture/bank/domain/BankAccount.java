package com.hexagonal.architecture.bank.domain;

import java.math.BigDecimal;

import lombok.Builder;

public class BankAccount {
    private Long id;
    private BigDecimal balance;
 
    @Builder
    public BankAccount(Long id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }
 
    public boolean withdraw(BigDecimal amount) {
        if(balance.compareTo(amount) < 0) {
            return false;
        }
 
        balance = balance.subtract(amount);
        return true;
    }
 
    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }
 
    public BigDecimal getBalance() {
        return balance;
    }

}
