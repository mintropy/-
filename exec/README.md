# 포팅 메뉴얼

# **Git 소스 활용하여 빌드**

- Docker-compose를 활용하여 빌드 및 실행을 함
- 최초 빌드 및 활용시 `docer-compose.dev.yml`을, 아니라면 `docer-compose.yml`을 활용하면 됨
    - `docer-compose.dev.yml`에서 MySQL 이미를 포함

```
$ docker-compose up docker-compose.dev.yml
$ docker-compose up docker-compose.yml
```

- 이후 Django의 DB 설정을 위한 migrate 및 꽃 데이터 입력

```
$ docker-compose run backend python manage.py migrate
$ docker-compose run backend python manage.py loaddata flower.json
```

- `http://127.0.0.1`로 접속

## **admin 이 필요한 경우**

```
$ docker-compose run backend python manage.py createsuperuser
```

- 해당 명령어를 통하여 관리자 계정 생성
- `http://127.0.0.1/admin/`으로 접속

# **.env**

- 환경 변수가 없더라도 기본적인 작동은 하지만, 추가적인 환경변수 설정은 다음과 같음

```
# backend/.env
DEBUG=True
SECRET_KEY='django-insecure-m^b)l3!)t4b2171u1xm=zl*_k0y$4%!ta=)de$nzo4k!ke79gr'
DB_NAME='testdb'
DB_USER='root'
DB_PASSWORD='horang22'
DB_HOST='localhost'
DB_PORT='3307'
kakao_client_id='057aa14f717c54ff1889493df84553ed'
host_base_url='http://127.0.0.1:8000/api/accounts/'
```

- 해당 .env 파일이 backend 디렉토리에 위치해야 함
    - `DB_` 와 관련한 설정은 Django에서 MySQL 사용과 관련한 부분
    - `kakao_client_id`는 kakao Oauth를 활용하기 위한 부분

# **APK**

- `app.apk` 를 설치하여 실행할 수 있음