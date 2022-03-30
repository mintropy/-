#! /bin/bash

python3 manage.py migrate --noinput
python3 manage.py collectstatic --noinput
# gunicorn back.wsgi:application --bind 0.0.0.0:8000