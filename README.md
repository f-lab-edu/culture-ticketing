# culture-ticketing

뮤지컬, 콘서트, 야구 등 문화생활 관련 티켓 예매 서비스

## 주요 기능

### USER
- 회원 가입을 할 수 있다.
- 로그인을 할 수 있다.
- 유저 프로필을 조회할 수 있다.
- 공연 목록 조회를 할 수 있다.
- 공연 좋아요 생성/삭제 할 수 있다.
- 공연 상세 정보를 조회할 수 있다.
- 공연 회차를 조회할 수 있다.
- 공연 날짜별 회차 목록을 조회할 수 있다.
- 해당 회차와 구역 내 공연 좌석 정보를 조회할 수 있다.
- 알림을 조회할 수 있다.

### ADMIN
- 장소를 등록할 수 있다.
- 출연자를 등록할 수 있다.
- 공연을 등록/조회할 수 있다.
- 공연 구역을 등록/조회할 수 있다.
- 공연 구역 등급을 등록/조회할 수 있다.
- 공연 좌석을 등록/조회할 수 있다.
- 공연 회차를 등록/조회할 수 있다.
- 공연 출연자를 등록/조회할 수 있다.

## Batch
- 매 시간 예약 시작 시간까지 1시간 남은 공연들을 좋아요한 회원들에게 실시간 알림을 보낸다.

## 사용 기술

- Java
- Spring Boot
- Spring Security
- Spring Batch
- JPA
- Querydsl
- MySQL
- Redis
- swagger 2
- Docker
- Kafka
- Jenkins
- Prometheus
- Grafana
- Ngrinder
- Naver Cloud Platform


## 아키텍처

<img width="670" alt="스크린샷 2024-08-13 오후 12 06 29" src="https://github.com/user-attachments/assets/9141688b-7ca1-462b-8d9d-15a8038afc66">


## ERD

<img width="652" alt="스크린샷 2024-08-13 오후 12 20 49" src="https://github.com/user-attachments/assets/ddf98b07-1a57-4c6b-aff4-579125cef882">


## UI
### USER
<img width="300" height="600" alt="스크린샷 2024-08-13 오전 11 15 34" src="https://github.com/user-attachments/assets/c4db98e2-2508-4e01-9558-158d1ec01200">
<img width="300" height="600" alt="스크린샷 2024-08-13 오전 11 15 42" src="https://github.com/user-attachments/assets/cdf18c64-6d1c-42c4-a1e0-5446890f109d">
<img width="300" height="600" alt="스크린샷 2024-08-13 오전 11 15 54" src="https://github.com/user-attachments/assets/f8a3ac88-d3a7-4a00-9cb2-f93bb1c7b474">
<img width="300" height="600" alt="스크린샷 2024-08-13 오전 11 16 05" src="https://github.com/user-attachments/assets/0a12ca19-0e6f-4027-b55f-070a3adab1de">
<img width="300" height="600" alt="스크린샷 2024-08-13 오전 11 16 13" src="https://github.com/user-attachments/assets/ebbd28c3-3574-4a58-bcb5-16fae0bd9ae3">
<img width="300" height="600" alt="스크린샷 2024-08-13 오전 11 16 31" src="https://github.com/user-attachments/assets/8e283a6a-5708-438e-b009-32e3f46b7a6c">
<img width="300" height="600" alt="스크린샷 2024-08-13 오전 11 16 45" src="https://github.com/user-attachments/assets/13b568fc-95f6-43cf-afc5-0c22d36528d4">
<img width="300" height="600" alt="스크린샷 2024-08-13 오전 11 16 53" src="https://github.com/user-attachments/assets/d85601e6-2432-465d-ac51-18c4412c8174">

</br>

### ADMIN

<img width="961" alt="스크린샷 2024-08-13 오전 11 18 02" src="https://github.com/user-attachments/assets/21e2674e-7ad8-4843-b742-af489fb9074a">
