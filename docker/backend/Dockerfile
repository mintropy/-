FROM python:3.7-slim

RUN apt-get update
RUN apt-get install -y gcc
RUN apt-get install -y default-libmysqlclient-dev
ENV PYTHONBUFFERED 1

WORKDIR /app/backend

RUN python -m pip install --upgrade pip

COPY ./backend/requirements.txt .
RUN pip install -r requirements.txt
# RUN pip install gunicorn

COPY ./backend .
# RUN python manage.py migrate
# RUN python manage.py collectstatic

# ENTRYPOINT ["./docker-entrypoint.sh"]
