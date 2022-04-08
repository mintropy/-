from django.urls import path
from .views.user import AccountViewSet


kakao_get_login = AccountViewSet.as_view({"get": "kakao_login"})
kakao_get_user_info = AccountViewSet.as_view({"get": "kakao_user_info"})
kakao_unlink = AccountViewSet.as_view({"post": "kakao_unlink"})

urlpatterns = [
    path("logins/", kakao_get_login),
    path("kakao/login/callback/", kakao_get_user_info),
    path("unlink/", kakao_unlink),
]
