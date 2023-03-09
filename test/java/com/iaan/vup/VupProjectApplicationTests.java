package com.iaan.vup;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;


/**
 * 1. Narrative
 * 모든 테스트 문장은 Narrative 하게 되어야 한다. 즉, 코드보다 인간의 언어와 유사하게 구성되어야 한다.
 * 
 * 2. Given / When / Then
 * 
 * */

@SpringBootTest
class VupProjectApplicationTests {
	

	@Test
	void contextLoads() {
	    // given
	    //given(bddMockitoPosting.hasStar()).thenReturn(true);
		
	    // when
	    //boolean actual = bddMockitoPosting.hasStar();
		
	    // then
	    //then(bddMockitoPosting).should().hasStar();
	}

}
