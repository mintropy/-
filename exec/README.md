# 산출물 기록 저장소

## 와이어 프레임 & 화면 정의서
- [피그마](https://www.figma.com/file/zq49A74YZ9E5p9uN7ghcRG/특화-PJT-team-library?node-id=0%3A1)

![화면정의서](%ED%99%94%EB%A9%B4%EC%A0%95%EC%9D%98%EC%84%9C1.jpg)

## ERD
- [ERD](https://www.notion.so/mintropy/ERD-f6074c6dd90c4306b3d17e3c07e3a165)

![ERD](ERD1.png)

- 각 유저가 일기를 작성
    - 일기와 함께 사진 업로드
    - 일기에 해당하는 꽃을 추천

## 시퀀스 다이어그램
```mermaid
sequenceDiagram
participant client
participant front
participant back
participant db

client ->> front : 카카오 로그인 요청
front ->> back: 카카오 로그인 요청(account/logins)
back ->> back : 카카오 서버에 인증 코드 요청 및 전달(redirect)
back ->> back : 인증 코드로 토큰 요청 및 전달(kauth.kakao.com/oauth/token)
back ->> db : 토큰(id) 기반 유저 확인 및 저장
db ->> back : 유저 정보 반환
back ->> front : 유저 정보 반환
front ->> client : 유저 정보 반환 및 로그인
```

```mermaid
sequenceDiagram
participant client
participant front
participant back
participant db

client ->> front : 요청
front ->> back : 요청
back ->> back : 카카오 인증
back ->> front : 토큰 유효하지 않음
front ->> front : 카카오 토큰 요청(refresh token으로)
front -->> client : 로그인(refresh token 만료시)
front ->> client : 토큰 저장
front ->> back : 요청
back ->> front : 요청 반환
front ->> client : 요청 반환
```

```mermaid
sequenceDiagram
participant client
participant front
participant back
participant db

client ->> front: 메인 페이지 요청(카카오 토큰)
front ->> back: 유저 인증 요청
back ->> back : 카카오에 인증 요청
back -->> front : 인증 안된경우 반환
back ->> db: 유저 일기 이력 요청
db->>back: 유저 일기 이력
back ->> front :  이력을 토대로 이달의 정원 작성
front->>client: 메인페이지(이달의 정원)
```

```mermaid
sequenceDiagram
participant client
participant front
participant back
participant db

client ->> front: 일기 작성
front ->> back : 일기 작성
back ->> db : 일기 내용 저장
back ->> back : 일기 내용 생성
back ->> front : 일기
front ->> client : 일기
```

## 시스템 구성도

### 프론트엔드
- Kotlin, Android

### 백엔드
- Python
- Django
    - DRF

### AI
- Python
- TensorFlow
