from datetime import date
import os

from django.shortcuts import get_object_or_404

from rest_framework import status
from rest_framework.response import Response
from rest_framework.viewsets import ViewSet

from .schema.diary import (
    diary_montly_schema,
    diary_daily_schema,
    diary_create_schema,
    diary_update_schema,
    diary_delete_schema,
)
from ..models import Diary, Flower
from ..serializers.diary import DiarySerializer
from accounts.views.user import get_kakao_user_info
from accounts.models import User
from .recommend_flower import recommend
from .caption_model import cap
from .translate import get_translate
from back.settings import BASE_DIR

class DiaryViewSet(ViewSet):
    model = Diary
    queryset = Diary.objects.all()
    serializer_class = DiarySerializer

    @diary_montly_schema
    def montly(self, request, year, month):
        token = request.headers.get("Authorization", "")
        user = get_kakao_user_info(token)
        diaries = Diary.objects.filter(
            user_id=user.id, date__year=year, date__month=month
        )
        serializer = DiarySerializer(diaries, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)

    @diary_daily_schema
    def daily(self, request, year, month, day):
        token = request.headers.get("Authorization", "")
        user = get_kakao_user_info(token)
        target_day = date(year, month, day)
        diary = get_object_or_404(Diary, user_id=user.id, date=target_day)
        serializer = DiarySerializer(diary)
        return Response(serializer.data, status=status.HTTP_200_OK)

    @diary_create_schema
    def create(self, request):
        token = request.headers.get("Authorization", "")
        user = get_kakao_user_info(token)
        try:
            target_day = date.fromisoformat(request.data['date'])
        except Exception:
            target_day = date.today()
        if target_day < date(1900, 1, 1) or target_day >= date(2050, 1, 1):
            return Response(status=status.HTTP_400_BAD_REQUEST)
        photo = request.FILES.get('photo', None)
        custom_content = request.data.get('custom_content', None)

        if not Diary.objects.filter(user=user, date=target_day).exists():
            if photo is None:
                return Response(status=status.HTTP_400_BAD_REQUEST)
            diary = Diary.objects.create(user=user, date=target_day, photo=photo)
            
            pathh = os.path.join(BASE_DIR,'media',str(target_day)[:4],str(target_day)[5:7],str(target_day)[8:],str(request.FILES['photo']))
            
            caption = cap(pathh)
            diary.en_content = caption
            diary.ko_content = get_translate(caption)
            
            flower_id=recommend(caption)
            
            flower = Flower.objects.get(id=flower_id)
            diary.flower = flower
            diary.save()
            user.flowers.add(flower)
            serializer = DiarySerializer(diary)
            return Response(serializer.data, status=status.HTTP_201_CREATED)

        diary = Diary.objects.get(user=user, date=target_day)
        if photo is not None:
            diary.photo = photo
        if custom_content is not None:
            diary.custom_content = custom_content
        serializer = DiarySerializer(diary)
        return Response(serializer.data, status=status.HTTP_201_CREATED)

    @diary_update_schema
    def update(self, request, year, month, day):
        token = request.headers.get("Authorization", "")
        user = get_kakao_user_info(token)

        target_day = date(year, month, day)
        diary = get_object_or_404(Diary, user=user, date=target_day)
        data = {
            "content": request.data.get("content", None),
            "user": user.id,
        }
        serializer = DiarySerializer(diary, data=data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    @diary_delete_schema
    def destroy(self, request, year, month, day):
        token = request.headers.get("Authorization", "")
        user = get_kakao_user_info(token)

        target_day = date(year, month, day)
        diary = get_object_or_404(Diary, user=user, date=target_day)
        diary.delete()
        diaries = Diary.objects.all()
        serializer = DiarySerializer(diaries, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)
