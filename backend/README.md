# Backend



## 📌목표

- Django Framework, Tensorflow 를 이해하고 활용할 수 있다.
- REST API등의 기술을 활용하여 MSA를 구현할 수 있다.
- 팀으로 작업하여 시너지를 내고, 새로운 아이디어 등을 추가하여 학습한 결과를 구현할 기획 및 설계

------

✅ 비화 서버를 구축

✅ 설계서

- 테이블 구조도 (ERD)
- 시퀀스 다이어그램
- 화면 설계서(WireFrame)
- REST API

✅ 소스 코드

- 프로젝트 관련 서버 전체 소스
- 이미지 캡셔닝, 추천 알고리즘 관련 소스

<hr>



## 개발

- Django 가상환경 및 서버 실행

## 가상환경 설정

```
# 가상환경 생성$ python -m venv venv
# 가상환경 실행$ source venv/scripts/activate
```

## 실행

```
$ pip install -r requirements.txt
$ python manage.py migrate
$ python manage.py loaddate flower.json
$ python manage.py runserver
```

- 접속 `127.0.0.1:8000`
- Python Coding Guide, Docstring Guide에 맞춰 개발 진행

<hr>



## AI

### 이미지 캡셔닝

Tensorflow의 이미지 캡션 모델을 활용하여 이미지를 입력 받으면 이미지에서 문장을 추출하는 캡셔닝 기능 수행.

캡셔닝이란 이미지에서 특징을 파악하고 이를 단어로 추출하여 이 단어들로 문장을 만들어 주는 기술



### NLP를 통한 콘텐츠 기반 추천 시스템

- 추천 알고리즘은 크게 추천할 학습 기반 데이터에 따라 콘텐츠 기반 필터링(content based filtering)과 협업 필터링(collaborative filtering)으로 분류된다.

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/5ebc0102-02fe-4168-a03c-541a220861c4/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220407%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220407T052219Z&X-Amz-Expires=86400&X-Amz-Signature=fe73d644645a897da5b6669d3b78d3e84d38d22d804a29dd3a8692efefa53860&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

------

- 현재 프로젝트에 도입된 추천 알고리즘은 콘텐츠 기반 알고리즘이다. 컨텐츠 기반 필터링은 기본적으로 컨텐츠를 구성하는 내용 즉, **텍스트에 기반하여 문서 유사도를 측정해 비슷한 다른 컨텐츠를 추천하는 것**을 말한다. 여기서 유사도란, 텍스트를 벡터화 시킨 후 벡터들간의 거리를 측정하는 것이다. 벡터들간의 거리를 측정하는 방식은 코사인 유사도를 이용해 사용했다.
- NLTK라는 자연어 처리 패키지를 사용하여 캡셔닝된 문장에서 핵심어를 추출하여 키워드로 저장하고, 꽃의 속성인 꽃말을 기준으로 필터링하기 때문에 벡터화시킨다. 여기서 벡터화는 Python Scikit Learn에 있는 CountVectorizer를 사용했다.

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/9f18c942-a4aa-41f1-aab2-50117bddb8d5/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220407%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220407T052235Z&X-Amz-Expires=86400&X-Amz-Signature=addf5dc9cf46f5fe74468c3e3d6ea933af71c580f76d9b0e6f483651ff7fa2e7&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

- 이후 cosine_similarity함수를 통해, 꽃말을 기준으로 유사도값을 계산하였다. 출력된 유사도값을 기준으로 캡셔닝 문장을 입력하면 꽃말을 추천해주는 사용자 함수를 정의했다.
