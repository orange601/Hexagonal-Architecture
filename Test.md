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


## 통합 테스트로 영속성 어댑터 테스트하기 ##
- 웹 어댑터를 통합 테스트하는 이유과 마찬가지로 영속성 어댑터도 통합 테스트를 적용한다.
- 어댑터의 로직만이 아니라 데이터베이스 매핑도 검증

````java
@DataJpaTest
@Import({AccountPersistenceAdapter.class, AccountMapper.class})
class AccountPersistenceAdapterTest {
    @Autowired
    private AccountPersistenceAdapter accountPersistenceAdapter;

    @Autowired
    private ActivityRepository activityRepository;

    @Test
    @Sql("AccountPersistenceAdapterTest.sql")
    void loadAccount() {
        Account account = accountPersistenceAdapter.loadAccount(new AccountId(1L), LocalDateTime.of(2018, 8, 10, 0, 0));

        assertAll(
                () -> assertThat(account.getActivityWindow().getActivities()).hasSize(2),
                () -> assertThat(account.calculateBalance()).isEqualTo(Money.of(500))
        );
    }
}
````
- @DataJpaTest: 데이터베이스 접근에 필요한 객체 네트워크(spring data repository 포함)를 인스턴스화해야함을 스프링에게 알려준다.
- @Import: 특정 객체가 이 네트워크에 추가됨을 명확히 표현
- @Sql("AccountPersistenceAdapterTest.sql"): sql 스크립트로 데이터베이스를 특정 상태로 만든다.

### 데이터베이스를 모킹하지 않는다. ###
- db를 모킹한다면 코드 수 커버리지는 동일하지만 실제 db와 연동했을 때 SQL 구문 오류나 db 테이블↔ 자바 객체 매핑 에러 등이 발생할 수 있다.
- 스프링에서는 기본적으로 테스트에서 in-memory database를 사용한다. (설정 필요 X)
- db마다 고유한 문법이 있기 때문에 실제 db에서 문제가 생길 수 있다.
- 실제 데이터베이스를 대상으로 진행해야 한다. ( 두 개의 다른 데이터베이스 시스템을 신경 쓸 필요도 없어진다. )

### 시스템 테스트로 주요 경로 테스트하기 ###
- 전체 애플리케이션 띄우고, API로 요청 보내고, 모든 계층이 잘 작동하는지 검증

````java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SendMoneySystemTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private LoadAccountPort loadAccountPort;

    private String url;

    @BeforeEach
    void setUp() {
        url = "/accounts/send/{sourceAccountId}/{targetAccountId}/{amount}";
    }

    @Test
    @Sql("SendMoneySystemTest.sql")
    void sendMoney() {
        Money initialSourceBalance = sourceAccount().calculateBalance();
        Money initialTargetBalance = targetAccount().calculateBalance();

        ResponseEntity response = whenSendMoney(sourceAccountId(), targetAccountId(), transferredAmount());

				then(response.getStatusCode())
                .isEqualTo(HttpStatus.OK);
				then(sourceAccount().calculateBalance())
                .isEqualTo(initialSourceBalance.minus(transferredAmount()));
				then(targetAccount().calculateBalance())
                .isEqualTo(initialTargetBalance.plus(transferredAmount()));
    }

		private ResponseEntity whenSendMoney(AccountId sourceAccountId, AccountId targetAccountId, Money money) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);

        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                Object.class,
                sourceAccountId,
                targetAccountId,
                money.amount()
        );
    }
}
````
