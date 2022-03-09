from django.urls import path, include
from .views import getUserInfo, kakaoGetLogin

urlpatterns = [
    path('logins', kakaoGetLogin),
    path('kakao/login/callback/', getUserInfo),
]