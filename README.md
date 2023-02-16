# Hexagonal-Architecture
- 헥사고날 아키텍처(Hexagonal Architecture), Hexagonal: 육각형
- 포트 및 어댑터 아키텍처라고도 함
- [계층형 아키텍처의 문제점](https://github.com/orange601/Layered-Architecture)을 보안하기 위해 탄생

![hexagol drawio](https://user-images.githubusercontent.com/24876345/219286682-25b347b4-b45c-44e5-a70f-d7de3ca7e5b3.png)


## 헥사고날 아키텍처의 장점 ##
1. 아키텍처 확장이 용이하다.
2. SOLID 원칙을 쉽게 적용할 수 있다.
3. 모듈 일부를 배포하는 게 용이하다.
4. 테스트를 위해 모듈을 가짜로 바꿀 수 있으므로 테스트가 더 안정적이고 쉽다.
5. 더 큰 비즈니스적 가치를 갖고 더 오래 지속되는 도메인 모델에 큰 관심을 둔다.

## 구분 ##
헥사고날 아키텍처는 내부(도메인)와 외부(인프라)로 구분된다.
- 내부 영역: 순수한 비즈니스 로직을 표현하며 캡슐화된 영역이고 기능적 요구사항에 따라 먼저 설계
- 외부 영역: 내부 영역에서 기술을 분리하여 구성한 영역이고 내부 영역 설계 이후 설계

포트는 내부 비즈니스 영역을 외부 영역에 노출한 API이고 인바운드(Inbound)/아웃바운드(Outbound) 포트로 구분된다.
- 인바운드 포트 - 내부 영역 사용을 위해 노출된 API
- 아웃바운드 포트 - 내부 영역이 외부 영역을 사용하기 위한 API

어댑터는 외부 세계와 포트 간 교환을 조정하고 역시 인바운드(Inbound)/아웃바운드(Outbound) 어댑터로 구분된다.
- 인바운드 어댑터 - 외부 애플리케이션/서비스와 내부 비즈니스 영역(인바운드 포트) 간 데이터 교환을 조정
- 아웃바운드 어댑터 - 내부 비즈니스 영역(아웃바운드 포트)과 외부 애플리케이션/서비스 간 데이터 교환을 조정


https://covenant.tistory.com/258

https://github.com/thombergs/buckpal/tree/master/src/main/java/io/reflectoring/buckpal
