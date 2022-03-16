from django.urls import path

from .views.user import AccountViewSet


kakaoGetLogin = AccountViewSet.as_view({"get": "kakao_login"})
getUserInfo = AccountViewSet.as_view({"get": "kakao_user_info"})
kakaoUnlink = AccountViewSet.as_view({"post": "kakao_unlink"})
user_information = AccountViewSet.as_view({"post": "update"})

urlpatterns = [
    path("", user_information),
    path("logins/", kakaoGetLogin),
    path("kakao/login/callback/", getUserInfo),
    path("unlink/", kakaoUnlink),
]
