package com.iaan.vup.bank;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.iaan.vup.module.bank.adapter.out.entity.BankAccountEntity;
import com.iaan.vup.module.bank.adapter.out.persistence.BankAccountRepository;
import com.iaan.vup.module.bank.application.service.BankAccountService;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

// 메서드가 지정한 회수 만큼 호출되었는 지의 여부를 확인하려면 times() 메서드를 사용하면 된다
// never() - 호출되지 않았는지 여부 검증
@ExtendWith(MockitoExtension.class)
public class BankApiUnitTest {
	@MockBean
	BankAccountService bankAccountService;
	private final BankAccountRepository bankAccountRepository = mock(BankAccountRepository.class);
	
	@Test
	void test() throws Exception {
		// given
		given(bankAccountRepository.existsById(6L)).willReturn(false);
		
		// when
		bankAccountRepository.save(BankAccountEntity.builder().id(6L).build());
		
		// then
		then(bankAccountRepository).should(times(1)).findBalanceById(6L);
	}
	
	void test2(){
		 // given
	    //given(skills.hasSkill()).willReturn(true);
        //
	    //// when
	    //boolean actual = person.hasSkill();
        //
	    //// then
	    //assertThat(actual).isTrue();
	}
	

}
