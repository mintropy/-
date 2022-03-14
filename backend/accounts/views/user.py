import requests
import os
import json

from django.shortcuts import redirect
import environ
from django.http import HttpResponse
from rest_framework import status
from rest_framework.viewsets import ViewSet
from rest_framework.response import Response

from accounts.models import User
from back.settings import BASE_DIR
from ..serializers.user import UserSerializer

from .schema.user import (
    kakao_login_schema,
    kakao_user_info_schema,
    kakao_unlink_schema
)

env = environ.Env(
    kakao_client_id=(str, "")
)
environ.Env.read_env(os.path.join(BASE_DIR, '.env'))


class AccountViewSet(ViewSet):
    model = User
    queryset = User.objects.all()

    @kakao_login_schema
    def kakao_login(self, request):
        client_id = env("kakao_client_id")
        redirect_url = "http://127.0.0.1:8000/api/accounts/kakao/login/callback/"
        base_url = "https://kauth.kakao.com/oauth/authorize?response_type=code"
        url = base_url + f"&client_id={client_id}" + f"&redirect_uri={redirect_url}"
        response = redirect(url)
        return response

    @kakao_user_info_schema
    def kakao_user_info(self, request):
        code = request.query_params.get("code", None)
        url = "https://kauth.kakao.com/oauth/token"
        data = {
            "grant_type": "authorization_code",
            "client_id": env("kakao_client_id"),
            "redirect_url": "http://127.0.0.1:8000/api/accounts/kakao/login/callback/",
            "client_secret": "none",
            "code": code,
        }
        headers = {"Content-type": "application/x-www-form-urlencoded;charset=utf-8"}
        response = requests.post(url, data=data, headers=headers)
        token_json = response.json()
        print(token_json)
        user_url = "https://kapi.kakao.com/v2/user/me"
        auth = "Bearer " + token_json["access_token"]
        headers = {
            "Authorization": auth,
            "Content-type": "application/x-www-form-urlencoded;charset=utf-8",
        }
        response = requests.get(user_url, headers=headers)
        user_info = response.text
        user_info = json.loads(user_info)
        print(user_info)
        user_id = str(user_info['id'])
        user_nickname = user_info['properties']['nickname']
        check_user = User.objects.filter(social_id=user_id)[0]
        if check_user:
            return HttpResponse(check_user)
        
        data = {
            "social" : "KA",
            "social_id" : user_id,
            "username" : user_nickname,
            "password" : user_id,
        }
        serializer = UserSerializer(data=data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_200_OK)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    @kakao_unlink_schema
    def kakao_unlink(self, request):
        TOKEN = 'bdxSavZVE3QZRWCIOf9qV8onVfEyYu_M0TSKLwo9dRoAAAF_iE18Yg' # access Token 직접 입력
        url = "https://kapi.kakao.com/v1/user/unlink"
        auth = "Bearer " + TOKEN 
        HEADER = {
            "Authorization": auth,
            "Content-Type" : "application/x-www-form-urlencoded",
        }
        res = requests.post(url, headers=HEADER)
        
        return Response(res)
