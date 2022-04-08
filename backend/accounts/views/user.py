import environ
import json
import os
import requests

from django.shortcuts import redirect
from django.contrib.auth.hashers import make_password
from rest_framework import status
from rest_framework.response import Response
from rest_framework.viewsets import ViewSet

from .schema.user import (
    kakao_login_schema,
    kakao_user_info_schema,
    kakao_unlink_schema,
    user_update_schema,
)
from ..serializers.user import UserSerializer
from accounts.models import User
from back.settings import BASE_DIR


env = environ.Env(
    host_base_url=(str, "http://j6d102.p.ssafy.io/api/accounts/"),
    kakao_client_id=(str, "057aa14f717c54ff1889493df84553ed")
)
environ.Env.read_env(os.path.join(BASE_DIR, ".env"))

host_base_url = env('host_base_url')
kakao_oauth_base_url = "https://kauth.kakao.com"
kakao_user_info_url = "https://kapi.kakao.com/v2/user/me"


def get_kakao_user_info(token: str) -> User:
    user_url = kakao_user_info_url
    headers = {
        "Authorization": token,
        "Content-type": "application/x-www-form-urlencoded;charset=utf-8",
    }
    response = requests.get(user_url, headers=headers)
    print(response)
    if response.status_code == status.HTTP_401_UNAUTHORIZED:
        return None
    user_info = response.text
    user_info = json.loads(user_info)
    user_id = user_info.get("id", None)
    if user_id is None:
        return None
    user, created = User.objects.get_or_create(social="KA", social_id=user_id)
    if created:
        user.username = user_info["properties"]["nickname"]
        user.password = make_password(str(user.social_id))
        user.save()
    return user


class AccountViewSet(ViewSet):
    model = User
    queryset = User.objects.all()
    serializer_class = UserSerializer
    permission_classes = []

    @kakao_login_schema
    def kakao_login(self, request):
        client_id = env("kakao_client_id")
        redirect_url = f"{host_base_url}kakao/login/callback/"
        oauth_url = f"{kakao_oauth_base_url}/oauth/authorize?response_type=code"
        url = f"{oauth_url}&client_id={client_id}&redirect_uri={redirect_url}"
        response = redirect(url)
        return response

    @kakao_user_info_schema
    def kakao_user_info(self, request):
        code = request.query_params.get("code", None)
        url = f"{kakao_oauth_base_url}/oauth/token"
        data = {
            "grant_type": "authorization_code",
            "client_id": env("kakao_client_id"),
            "redirect_url": f"{host_base_url}/kakao/login/callback/",
            "client_secret": "none",
            "code": code,
        }
        headers = {"Content-type": "application/x-www-form-urlencoded;charset=utf-8"}
        response = requests.post(url, data=data, headers=headers)
        token_json = response.json()

        token = "Bearer " + token_json["access_token"]
        user = get_kakao_user_info(token)
        return Response(token_json, status=status.HTTP_200_OK)

    @kakao_unlink_schema
    def kakao_unlink(self, request):
        token = request.headers.get("Authorization", "")
        user = get_kakao_user_info(token)
        url = "https://kapi.kakao.com/v1/user/unlink"
        HEADER = {
            "Authorization": token,
            "Content-Type": "application/x-www-form-urlencoded",
        }
        response = requests.post(url, headers=HEADER)
        if response.status_code == status.HTTP_401_UNAUTHORIZED:
            return Response(status=status.HTTP_401_UNAUTHORIZED)
        user.delete()
        return Response(status=status.HTTP_200_OK)

    @user_update_schema
    def update(self, request):
        token = request.headers.get("Authorization", "")
        user = get_kakao_user_info(token)
        data = {
            "email": request.data.get("email", user.email),
            "username": request.data.get("username", user.username),
        }
        serializer = UserSerializer(user, data=data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
