# 산출물 기록 저장소

## 와이어 프레임 & 화면 정의서
- [피그마](https://www.figma.com/file/zq49A74YZ9E5p9uN7ghcRG/특화-PJT-team-library?node-id=0%3A1)
- [화면 정의서](https://mintropy.notion.site/UI-0e6a29fdbc5346e2ad4a3544df18f61a)
![화면정의서](%ED%99%94%EB%A9%B4%EC%A0%95%EC%9D%98%EC%84%9C1.jpg)

## ERD
- [ERD](https://www.notion.so/mintropy/ERD-f6074c6dd90c4306b3d17e3c07e3a165)

![ERD](ERD1.png)

- 각 유저가 일기를 작성
    - 일기와 함께 사진 업로드
    - 일기에 해당하는 꽃을 추천

## 시퀀스 다이어그램
- [시퀀스 다이어그램](https://www.notion.so/mintropy/5551842c096a450a83cc807df7069583)

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

client ->> front: 일기 작성(화분 선택)
front ->> client: 앨범에서 사진 선택
client ->> front : 사진 전달
front ->> back : 사진 전달
back ->> back : 사진에서 문장 출력(영어)
back ->> back : 캡셔닝에서 꽃 추천
back ->> back : 문장 번역(API)
back ->> db : 사진 & 캡셔닝 문장 정리

back ->> front : 캡셔닝 문장(한글)
back ->> front : 오늘의 꽃
front ->> client : 일기 작성 페이지
client ->> front : 작성 일기 반환
front ->> back : 일기 작성
back ->> back : 일기 맞춤법 API

back ->> db : 일기 내용 저장
db ->> back : 일기 내용
back ->> front : 일기(사진 + 캡셔닝 + 개인일기)
front ->> client : 일기
```

```mermaid
sequenceDiagram
participant client
participant front
participant back
participant db

client ->> front: 일기 작성
front ->> client: 앨범에서 사진 선택
client ->> front : 사진 전달
front ->> back : 사진 전달
back ->> db : 사진 수정

back ->> front : 사진
front ->> client : 일기 작성 페이지
client ->> front : 작성 일기 반환
front ->> back : 일기 작성
back ->> back : 일기 맞춤법 API
back ->> db : 일기 내용 저장
db ->> back : 일기 내용
back ->> front : 일기
front ->> client : 일기
```

## 시스템 구성도
![diagram](시스템%20구성도.png)
