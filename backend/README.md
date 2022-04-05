# Backend# Backend

## 가상환경 설정
```bash
# 가상환경 생성
$ python -m venv venv
# 가상환경 실행
$ source venv/scripts/activate
```

## 실행
```bash
$ pip install -r requirements.txt
$ python manage.py migrate
$ python manage.py loaddate flower.json
$ python manage.py runserver
```

- 접속 `127.0.0.1:8000`
