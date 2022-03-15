from django.urls import path, include
# from .views2 import getUserInfo, kakaoGetLogin, kakaoUnlink

from .views.user import AccountViewSet

kakaoGetLogin = AccountViewSet.as_view({'get':'kakao_login'})
getUserInfo = AccountViewSet.as_view({'get':'kakao_user_info'})
kakaoUnlink = AccountViewSet.as_view({'post':'kakao_unlink'})
kakaoLoginFinish = AccountViewSet.as_view({'get':'kakao_finish'})

urlpatterns = [
    # path('logins/', kakaoGetLogin),
    # path('kakao/login/callback/', getUserInfo),
    # path('unlink/', kakaoUnlink),
    #
    path('logins/', kakaoGetLogin),
    path('kakao/login/callback/', getUserInfo),
    path('unlink/', kakaoUnlink),
    path('kakao/login/finish/', kakaoLoginFinish),
]
