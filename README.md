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
- 애플리케이션과 어댑터 간에 통신을 위한 부분
   
   #### 인바운드 포트 ####
   + 유스케이스
   #### 아웃바운드 포트 ####
   + repository

### 3. 어플리케이션(Application) ###
- 내부 영역
   
    #### 유스케이스 ####
    + 시스템을 사용하는 클라이언트가 그 시스템을 통해 하고자하는 것, 도메인의 비즈니스 로직을 오케스트레이션하는 역할 (인풋 포트) (예시 : 기존계층형 서비스 역할)
    #### 엔티티 ####
    + 도메인에서 다루는 핵심 개체 (예시 : 기존 계층형 아키텍처에서 JPA로 관리되는 엔티티)

## 헥사고날 아키텍처의 장점 ##
1. 아키텍처 확장이 용이하다.
2. SOLID 원칙을 쉽게 적용할 수 있다.
3. 모듈 일부를 배포하는 게 용이하다.
4. 테스트를 위해 모듈을 가짜로 바꿀 수 있으므로 테스트가 더 안정적이고 쉽다.
5. 더 큰 비즈니스적 가치를 갖고 더 오래 지속되는 도메인 모델에 큰 관심을 둔다.



