from datetime import date

from django.shortcuts import get_object_or_404
from rest_framework import status
from rest_framework.response import Response
from rest_framework.viewsets import ViewSet

from .schema.photo import (
    photo_list_schema,
    photo_create_schema,
    photo_retrieve_schema,
    photo_destroy_schema,
)

from ..models import Diary, Photo
from ..serializers.photo import PhotoSerializier
from accounts.models import User
from accounts.views.user import get_kakao_user_info


class PhotoViewSet(ViewSet):
    model = Photo
    queryset = Diary.objects.all()
    serializer_class = PhotoSerializier

    @photo_create_schema
    def create(self, request):
        # API 로직 정리
        # 사진 생성 >> 일기 최초 작성인지 확인
        # 일기 최초 작성 : 캡셔닝 >> 꽃
        # 일기 최초 작성 아님 : 캡셔닝
        token = request.headers.get("Authorization", "")
        user_info = get_kakao_user_info(token)
        if not user_info:
            return Response(status=status.HTTP_401_UNAUTHORIZED)
        user_id = user_info.get("id", None)
        user = get_object_or_404(User, social_id=user_id)
        try:
            target_day = date.fromisoformat(request.data['date'])
        except Exception:
            target_day = date.today()

        photo = request.FILES.get('photo', None)
        if photo is None:
            return Response(status=status.HTTP_400_BAD_REQUEST)
        diary, created = Diary.objects.get_or_create(user=user, date=target_day)

        # 사진 업로드
        diary.photo = photo

        # 반환 데이터
        data = {}
        # 캡셔닝

        if created:
            # 꽃 추천
            pass

        return Response(data, status=status.HTTP_201_CREATED)

    @photo_retrieve_schema
    def retrieve(self, request, diary_id):
        if not Diary.objects.filter(pk=diary_id).exists():
            return Response(status=status.HTTP_404_NOT_FOUND)
        photo = Photo.objects.filter(dairies=diary_id)
        serializer = PhotoSerializier(photo)
        return Response(serializer.data, status=status.HTTP_200_OK)

    @photo_destroy_schema
    def destroy(self, request, year, month, day):
        token = request.headers.get("Authorization", "")
        user_info = get_kakao_user_info(token)
        if not user_info:
            return Response(status=status.HTTP_401_UNAUTHORIZED)
        user_id = user_info.get("id", None)
        user = get_object_or_404(User, social_id=user_id)

        target_day = date(year, month, day)
        diary = get_object_or_404(Diary, user=user, date=target_day)
        deletion_list = []
        return Response(status=status.HTTP_200_OK)
