#!/bin/bash
#해당 명령은 dockerize를 이용해 mysql_service 컨테이너의 3306포트에 20초동안 tcp연결을 걸며 대기하는 명령어이다. 
dockerize -wait tcp://db:3306 -timeout 20s


echo "Apply database migrations"
python manage.py migrate

echo "Starting django server"
# python manage.py runserver 0.0.0.0:8000