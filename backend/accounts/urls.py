from django.urls import path, include
from .views import getUserInfo, kakaoGetLogin, kakaoUnlink

urlpatterns = [
    path('logins', kakaoGetLogin),
    path('kakao/login/callback/', getUserInfo),
    path('unlink', kakaoUnlink),
]