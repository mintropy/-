import requests
import os

from django.shortcuts import redirect
from django.http import HttpResponse
import environ
from rest_framework.decorators import api_view, permission_classes
from rest_framework.permissions import AllowAny

from back.settings import BASE_DIR

env = environ.Env(
    kakao_client_id=(str, "")
)
environ.Env.read_env(os.path.join(BASE_DIR, '.env'))


@api_view(["GET"])
@permission_classes(
    [
        AllowAny,
    ]
)
def kakaoGetLogin(request):
    print("@#!@$")
    CLIENT_ID = env("kakao_client_id")
    REDIRET_URL = "http://127.0.0.1:8000/api/accounts/kakao/login/callback/"
    base_url = "https://kauth.kakao.com/oauth/authorize?response_type=code"
    url = base_url + f"&client_id={CLIENT_ID}" + f"&redirect_uri={REDIRET_URL}"
    res = redirect(url)
    print(res)
    return res


@api_view(["GET"])
@permission_classes(
    [
        AllowAny,
    ]
)
def getUserInfo(reqeust):
    print("###########")
    CODE = reqeust.query_params.get("code", None)
    url = "https://kauth.kakao.com/oauth/token"
    res = {
        "grant_type": "authorization_code",
        "client_id": env("kakao_client_id"),
        "redirect_url": "http://127.0.0.1:8000/api/accounts/kakao/login/callback/",
        "client_secret": "none",
        "code": CODE,
    }
    headers = {"Content-type": "application/x-www-form-urlencoded;charset=utf-8"}
    response = requests.post(url, data=res, headers=headers)
    # 그 이후 부분
    tokenJson = response.json()
    print("$#")
    print(tokenJson)
    userUrl = "https://kapi.kakao.com/v2/user/me"  # 유저 정보 조회하는 uri
    auth = "Bearer " + tokenJson["access_token"]  ## 'Bearer '여기에서 띄어쓰기 필수!!
    print("##")
    print(auth)
    HEADER = {
        "Authorization": auth,
        "Content-type": "application/x-www-form-urlencoded;charset=utf-8",
    }
    res = requests.get(userUrl, headers=HEADER)
    return HttpResponse(res.text)


@api_view(["POST"])
@permission_classes(
    [
        AllowAny,
    ]
)
def kakaoUnlink(request):
    TOKEN = 'c7fUP-PS_Tow2M0Rbra4y5ear5BoChgyDkeTZQo9cpgAAAF_fPtJdQ'
    url = "https://kapi.kakao.com/v1/user/unlink"
    auth = "Bearer " + TOKEN 
    HEADER = {
        "Authorization": auth,
        "Content-Type" : "application/x-www-form-urlencoded",
    }
    res = requests.post(url, headers=HEADER)
    
    return HttpResponse(res)