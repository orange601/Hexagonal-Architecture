
# 웹 어댑터 #
- 어댑터: 외부 세계와의 소통 창구
- 웹 어댑터: 웹 인터페이스

## 웹 어댑터의 책임 ##
- 어떠한 도메인 로직도 수행하지 않는다.
- HTTP 관련 작업 같은 애플리케이션 코어가 신경 쓸 필요 없는 많은 일들을 수행한다.

1. HTTP 요청을 자바 객체로 매핑
2. 파라미터, 콘텐츠를 객체로 역직렬화
3. 인증 및 권한 검사
4. 입력 유효성 검증
5. 유스케이스의 입력 모델 유효성 검증과는 다르다.
6. 웹 어댑터의 입력 모델을 유스케이스의 입력 모델로 변환할 수 있는가?
7. 입력을 유스케이스의 입력 모델로 매핑
8. 유스케이스 호출
9. 유스케이스의 출력을 HTTP로 매핑
10. HTTP 응답을 반환

## 의존성 역전 ##
- 제어 흐름: adapter.in.web.Controller → application.service.Service
- 호출
  1. adapter.in.web.Controller →(직접 호출) application.service.Service
  2. adapter.in.web.Controller →(호출) application.port.in.IncomingPort ←(구현) application.service.Service

## Port의 존재 이유 ##

## 1. 코어가 외부와 통신할수 있는 곳에 대한 명세 ##
- 외부와 어떤 통신이 일어나는지 정확히 알 수 있다.
- 레거시 코드를 다루는 유지보수 엔지니어

## 2. 애플리케이션이 웹 어댑터 쪽으로 실시간 데이터를 주어야 하는 경우 ex. 웹 소켓 ##
- 제어 흐름: 웹 어댑터 ← 애플리케이션 코어
- adapter.in.web.WebSocketController →(구현) applicatoin.port.out.WebSocketPort ←(호출) application.service.Service




## 웹 어댑터의 책임 ##
- 어떠한 도메인 로직도 수행하지 않는다.
- HTTP 관련 작업 같은 애플리케이션 코어가 신경 쓸 필요 없는 많은 일들을 수행한다.

