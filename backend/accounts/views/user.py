import environ
import json
import os
import requests

from django.shortcuts import redirect
from rest_framework import status
from rest_framework.response import Response
from rest_framework.viewsets import ViewSet

from .schema.user import kakao_login_schema, kakao_user_info_schema, kakao_unlink_schema, user_update_schema
from ..serializers.user import UserSerializer
from accounts.models import User
from back.settings import BASE_DIR

env = environ.Env(
    host_base_url=(str, "http://127.0.0.1:8000/api/accounts/"),
    kakao_client_id=(str, "")
)
environ.Env.read_env(os.path.join(BASE_DIR, ".env"))

host_base_url = env('host_base_url')
kakao_oauth_base_url = "https://kauth.kakao.com"
kakao_user_info_url = "https://kapi.kakao.com/v2/user/me" 


def get_kakao_user_info(token: str):
    user_url = kakao_user_info_url
    headers = {
        "Authorization": token,
        "Content-type": "application/x-www-form-urlencoded;charset=utf-8",
    }
    response = requests.get(user_url, headers=headers)
    if response.status_code == status.HTTP_401_UNAUTHORIZED:
        return Response(status=status.HTTP_401_UNAUTHORIZED)
    user_info = response.text
    user_info = json.loads(user_info)
    return user_info


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
        user_info = get_kakao_user_info(token)
        user_id = str(user_info["id"])
        user_nickname = user_info["properties"]["nickname"]
        check_user = User.objects.filter(social_id=user_id)
        if check_user:
            return Response(token_json, status=status.HTTP_200_OK)
        data = {
            "social": "KA",
            "social_id": user_id,
            "username": user_nickname,
            "password": user_id,
        }
        serializer = UserSerializer(data=data)
        if serializer.is_valid():
            serializer.save()
            return Response(token_json, status=status.HTTP_200_OK)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    @kakao_unlink_schema
    def kakao_unlink(self, request):
        token = request.headers.get('Authorization', '')
        user_info = get_kakao_user_info(token)
        user_id = str(user_info["id"])
        if not User.objects.filter(social_id=user_id).exists():
            return Response(status=status.HTTP_400_BAD_REQUEST)
        user = User.objects.get(social_id=user_id)
        url = "https://kapi.kakao.com/v1/user/unlink"
        auth = "Bearer " + token
        HEADER = {
            "Authorization": auth,
            "Content-Type": "application/x-www-form-urlencoded",
        }
        response = requests.post(url, headers=HEADER)
        if response.status_code == status.HTTP_401_UNAUTHORIZED:
            return Response(status=status.HTTP_401_UNAUTHORIZED)
        user.delete()
        return Response(status=status.HTTP_200_OK)

    @user_update_schema
    def update(self, request):
        token = request.headers.get('Authorization', '')
        user_info = get_kakao_user_info(token)
        user_id = str(user_info["id"])
        if not User.objects.filter(social_id=user_id).exists():
            return Response(status=status.HTTP_400_BAD_REQUEST)
        user = User.objects.get(social_id=user_id)
        data = {
            "email": request.data.get("email", user.email),
            "username": request.data.get("username", user.username),
        }
        serializer = UserSerializer(user, data=data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
