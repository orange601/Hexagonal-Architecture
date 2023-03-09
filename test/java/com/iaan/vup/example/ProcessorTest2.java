package com.iaan.vup.example;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ProcessorTest2 {
	@MockBean
	MyService myService2;
	
	@Test
	public void processTest2() {
		// given // 이는 테스트 대상이 A 상태에서 출발하며(Given) 
	    String processName = "dummy-process-name";
	    given(myService2.doSomething(processName)).willReturn(10);
	    
	    // when // 어떤 상태 변화를 가했을 때(When)
	    MyProcessor myProcessor = new MyProcessor(processName, myService2);
	    myProcessor.process();
	    
	    // then // 기대하는 상태로 완료되어야 한다(Then)
	    then(myService2).should().doSomething(processName);
	}

}
