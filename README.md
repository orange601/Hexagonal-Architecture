# Hexagonal-Architecture
- 헥사고날 아키텍처(Hexagonal Architecture), Hexagonal: 육각형
- 포트 및 어댑터 아키텍처라고도 함
- [계층형 아키텍처의 문제점](https://github.com/orange601/Layered-Architecture)을 보안하기 위해 탄생
- 인터페이스나 기반 요소(infrastructure)의 변경에 영향을 받지 않는 핵심 코드를 만들고 이를 견고하게 관리하는 것이 목표다.
- 출처: https://covenant.tistory.com/258

<img src = "https://user-images.githubusercontent.com/24876345/219293566-f9bd82b3-2e08-468e-a91c-3e30fbed9168.png" width="880px">


## 구조 ##

### 1. 어댑터(Adapter) ###
- 포트의 구현체
- 어댑터가 애플리케이션을 직접 참조하지 않고 포트에 의존

    #### 인바운드 어댑터 (= driving adapter 혹은 incoming adapter) ####
    + 계층형 아키텍처에서 컨트롤러를 의미

    #### 아웃바운드 어댑터 (= driven adapter 혹은 outgoing adapter) ####
    + repository 구현체

### 2. 포트(Port) ###
- 인터페이스로 구성
- 변경이 잦은 어댑터와 애플리케이션의 결합도를 낮추는 역할
   
   #### 인바운드 포트 ####
   + 유스케이스
   #### 아웃바운드 포트 ####
   + repository 

### 3. 어플리케이션(Application) ###
- 애플리케이션은 핵심 로직에 가까우므로 결합도를 낮추는 것이 매우 중요하다.
- 애플리케이션은 도메인에 의존하지만 도메인은 애플리케이션과 어댑터에 전혀 의존하지 않는다.
- 따라서 애플리케이션이나 어댑터가 변경되어도 핵심 로직인 도메인은 아무런 영향을 받지 않는다.
   
    #### 유스케이스 ####
    + 시스템을 사용하는 클라이언트가 그 시스템을 통해 하고자하는 것, 도메인의 비즈니스 로직을 오케스트레이션하는 역할 (인풋 포트) (예시 : 기존계층형 서비스 역할)
    #### 엔티티 ####
    + 도메인에서 다루는 핵심 개체 (예시 : 기존 계층형 아키텍처에서 JPA로 관리되는 엔티티)

### 4. 도메인(Domain) ###
- 외부를 향한 의존성이 없다.
- 특정 기술에 종속적이지 않다. 


## 헥사고날 아키텍처의 장점 ##
1. 아키텍처 확장이 용이하다.
2. SOLID 원칙을 쉽게 적용할 수 있다.
3. 모듈 일부를 배포하는 게 용이하다.
4. 테스트를 위해 모듈을 가짜로 바꿀 수 있으므로 테스트가 더 안정적이고 쉽다.
5. 더 큰 비즈니스적 가치를 갖고 더 오래 지속되는 도메인 모델에 큰 관심을 둔다.


자세한 내용은 [LINE Engineering](https://engineering.linecorp.com/ko/blog/port-and-adapter-architecture/) 참고


## 구현 ##
1. 도메인 중심의 아키텍처이므로 도메인 엔티티를 먼저 만든 후에 이를 바탕으로 유스케이스를 구현


## 출처 및 참고 ##
- https://injae7034.github.io/java/wooa_study_04/
- https://engineering.linecorp.com/ko/blog/port-and-adapter-architecture/
- https://covenant.tistory.com/258
