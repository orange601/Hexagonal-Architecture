package com.iaan.vup.example;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
public class ProcessorTest {
	@Mock
	MyService myService2;
	
	@Test // processTest2와 같음
	public void processTest1() {
	    MyService myService = Mockito.mock(MyService.class);
	    String processName = "dummy-process-name";
	    BDDMockito.given(myService.doSomething(processName)).willReturn(10);
	    MyProcessor myProcessor = new MyProcessor(processName, myService);
	    myProcessor.process();
	    BDDMockito.then(myService).should().doSomething(processName);
	}
	
	@Test // processTest1과 같음
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
