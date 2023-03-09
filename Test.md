# 테스트 피라미드 #
- 육각형 아키텍처에서의 테스트 피라미드

1. 단위 테스트
    - 피라미드의 토대
    - 한 클래스를 인스턴스화하고, 해당 클래스의 인터페이스를 통해 검증
    - 테스트 중인 클래스(Class Under Test, CUT)가 다른 클래스를 의존한다면 모킹(mocking)한다.
2. 통합 테스트
    - 연결된 여러 unit을 인스턴스화하고, 시작점이 되는 클래스의 인터페이스로 데이터를 보낸 후 유닛들의 네트워크가 잘 작동하는지 검증
    - 두 계층 간의 경계를 걸칠 수 있다.
3. 시스템 테스트
    - 특정 유스케이스가 전 계층에서 잘 동작하는지 검증
    - 애플리케이션의 모든 객체 네트워크 가동
4. E2E(end-to-end) 테스트
    - UI를 포함한 테스트


## 단위 테스트로 도메인 엔티티 테스트하기 ##
- 도메인 엔티티에 녹아 있는 비즈니스 규칙 검증
- 도메인 엔티티의 행동은 다른 클래스에 거의 의존하지 않기 때문에 다른 종류의 테스트는 필요하지 않다.

### Mocking ###
- Mockito 라이브러리로 given.. 메서드의 mock 객체 생성
- then(): mock 객체에 대해 특정 메서드가 호출되었는지 검증할 수 있는 메서드
- 특정 상태를 검증하는 것이 아니라, 모킹된 객체의 특정 메서드와 상호작용했는가를 검증
- 단점: 테스트가 코드의 행동 변경뿐만 아니라 구조 변경에도 취약해진다. = 테스트 변경 확률 
    + 모든 동작보다는 핵심만 골라서 테스트하기

### BDD ###
- given: 인스턴스 생성하고 적절한 상태로 만들기
- when: 유스케이스의 메서드 호출
- then: 트랜잭션이 성공적이었는지 확인, 특정 메서드가 호출되었는지 검증


## 통합 테스트로 웹 어댑터 테스트 ##

````java
@WebMvcTest(controllers = {SendMoneyController.class})
class SendMoneyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SendMoneyUseCase sendMoneyUseCase;

    @Test
    void sendMoney() throws Exception {
        String sendMoneyUrl = "/accounts/send/{sourceAccountId}/{targetAccountId}/{amount}";
        long sourceAccountId = 1L;
        long targetAccountId = 2L;
        long amount = 500;
        ResultActions resultActions = mockMvc.perform(
                post(sendMoneyUrl, sourceAccountId, targetAccountId, amount)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(status().isOk());
        then(sendMoneyUseCase).should()
                .sendMoney(eq(new SendMoneyCommand(
                        new AccountId(sourceAccountId),
                        new AccountId(targetAccountId),
                        Money.of(amount)
                )));
    }
}
````
- @WebMvcTest: MockMvc에 관한 설정을 자동으로 수행, 특정 컨트롤러 클래스와 관련 설정들을 스캔한다.
- MockMvc: 서버 입장에서 구현한 API를 통해 비즈니스 로직이 문제없이 수행되는지 테스트. Servlet Container를 생성하지 않는다.
- @MockBean: 사용할 서비스 인터페이스를 모킹
- resultActions.andExpert(): HTTP 응답이 기대한 상태를 반환했는가
- then()
    + SendMoneyCommand: 유스케이스에 구문적으로 ㅠ효한 입력인가
    + 유스케이스가 실제로 호출되었는가

### 왜 단위 테스트가 아닌 통합 테스트일까? ###
- 컨트롤러 클래스만 테스트하는 것처럼 보이지만, @WebMvcTest가 스프링이 특정 요청 경로, 자바↔ JSON 매핑, HTTP 입력 검증 등 필요한 모든 네트워크를 인스턴스화한다. 
- 웹 컨트롤러는 이 네트워크의 일부로서 동작하는 것이다.
- 웹 컨트롤러는 스프링 프레임워크와 강하게 결합되어있기 때문에 단위 테스트보다는 프레임워크와 통합된 상태로 테스트하는 것이 합리적이다.
- 프레임워크를 구성하는 요소들이 프로덕션 환경에서 정상적으로 작동할지 확신할 수 없다.
