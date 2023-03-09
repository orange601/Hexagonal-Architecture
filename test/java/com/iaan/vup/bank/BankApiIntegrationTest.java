package com.iaan.vup.bank;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.iaan.vup.module.bank.adapter.out.persistence.BankAccountRepository;
import com.iaan.vup.module.bank.domain.BankAccount;

@SpringBootTest
public class BankApiIntegrationTest {
	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	@Test
	@DisplayName("BULK INSERT 테스트")
	@Transactional // insert 후 롤백
	void bulkinsert() {
		List<BankAccount> account = new ArrayList<>();
		account.add(BankAccount.builder().id(3L).balance(new BigDecimal("55")).build());
		bankAccountRepository.bulkInsert(account);
	}
	
	@Test
	@DisplayName("JPQL 테스트")
	void name() {
		BigDecimal b = bankAccountRepository.findBalanceById(1L);
	}

}
