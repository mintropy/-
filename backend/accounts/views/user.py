import requests
import os

from django.shortcuts import redirect
from django.http import HttpResponse
import environ
from rest_framework.viewsets import ViewSet
from rest_framework.response import Response

from accounts.models import User
from back.settings import BASE_DIR

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
        redirect_url = "http://127.0.0.1:8000/accounts/kakao/login/callback/"
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
            "redirect_url": "http://127.0.0.1:8000/accounts/kakao/login/callback/",
            "client_secret": "none",
            "code": code,
        }
        headers = {"Content-type": "application/x-www-form-urlencoded;charset=utf-8"}
        response = requests.post(url, data=data, headers=headers)
        token_json = response.json()
        user_url = "https://kapi.kakao.com/v2/user/me"
        auth = "Bearer " + token_json["access_token"]
        headers = {
            "Authorization": auth,
            "Content-type": "application/x-www-form-urlencoded;charset=utf-8",
        }
        response = requests.get(user_url, headers=headers)
        user_info = response.text
        user_id = user_info['id']
        user_nickname = user_info['properties']
        
        return HttpResponse(response.text)
