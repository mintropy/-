from datetime import date
import nltk
import os

from django.shortcuts import get_object_or_404

from djangorestframework_camel_case.parser import CamelCaseJSONParser
from djangorestframework_camel_case.render import CamelCaseJSONRenderer

from rest_framework import status
from rest_framework.response import Response
from rest_framework.viewsets import ViewSet
from rest_framework.parsers import MultiPartParser, FileUploadParser, FormParser

from .schema.diary import (
    diary_montly_schema,
    diary_daily_schema,
    diary_create_schema,
    diary_update_schema,
    diary_delete_schema,
)
from .caption_model import cap
from .recommend_flower import recommend
from .translate import get_translate
from ..models import Diary, Flower
from ..serializers.diary import DiarySerializer, MonthDiarySerializer
from accounts.views.user import get_kakao_user_info
from back.settings import BASE_DIR
from .hanspell import spell_checker


class DiaryViewSet(ViewSet):
    model = Diary
    queryset = Diary.objects.all()
    serializer_class = DiarySerializer
    renderer_classes = [CamelCaseJSONRenderer]
    parser_classes = [
        CamelCaseJSONParser,
        MultiPartParser,
        FileUploadParser,
        FormParser,
    ]

    def nltk_download(self, rqeust):
        nltk.download("popular")
        return Response(status=status.HTTP_200_OK)

    @diary_montly_schema
    def montly(self, request, year, month):
        token = request.headers.get("Authorization", "")
        user = get_kakao_user_info(token)
        if user is None:
            return Response(status=status.HTTP_401_UNAUTHORIZED)
        diaries = Diary.objects.filter(
            user_id=user.id, date__year=year, date__month=month
        ).order_by('date')
        serializer = MonthDiarySerializer(diaries, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)

    @diary_daily_schema
    def daily(self, request, year, month, day):
        token = request.headers.get("Authorization", "")
        user = get_kakao_user_info(token)
        if user is None:
            return Response(status=status.HTTP_401_UNAUTHORIZED)
        target_day = date(year, month, day)
        diary = get_object_or_404(Diary, user_id=user.id, date=target_day)
        serializer = DiarySerializer(diary)
        return Response(serializer.data, status=status.HTTP_200_OK)

    @diary_create_schema
    def create(self, request):
        token = request.headers.get("Authorization", "")
        user = get_kakao_user_info(token)
        if user is None:
            return Response(status=status.HTTP_401_UNAUTHORIZED)
        try:
            target_day = date.fromisoformat(request.data["date"].replace('"', ""))
        except Exception:
            target_day = date.today()
        if target_day < date(1900, 1, 1) or target_day >= date(2050, 1, 1):
            return Response(status=status.HTTP_400_BAD_REQUEST)
        photo = request.FILES.get("photo", None)

        if not Diary.objects.filter(user=user, date=target_day).exists():
            if photo is None:
                return Response(status=status.HTTP_400_BAD_REQUEST)
            diary = Diary.objects.create(user=user, date=target_day, photo=photo)
            pathh = os.path.join(
                BASE_DIR,
                "media",
                str(target_day)[:4],
                str(target_day)[5:7],
                str(target_day)[8:],
                str(request.FILES["photo"]),
            )
            en_caption = cap(pathh).replace("<unk>", "").replace("  ", " ")
            flower_id = recommend(en_caption)
            flower = Flower.objects.get(id=flower_id)

            diary.en_content = en_caption
            diary.ko_content = get_translate(en_caption)
            diary.flower = flower
            diary.save()
            user.flowers.add(flower)
            serializer = DiarySerializer(diary)
            return Response(serializer.data, status=status.HTTP_201_CREATED)

        diary = Diary.objects.get(user=user, date=target_day)
        custom_content = request.data.get("customContent", None)
        if photo is not None:
            diary.photo = photo
        if custom_content is not None:
            diary.custom_content = custom_content.replace('"', "")
        diary.save()
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

    def spell_check(self, request):
        token = request.headers.get("Authorization", "")
        user = get_kakao_user_info(token)
        if user is None:
            return Response(status=status.HTTP_401_UNAUTHORIZED)
        try:
            target_day = date.fromisoformat(request.data["date"].replace('"', ""))
        except Exception:
            target_day = date.today()
        if target_day < date(1900, 1, 1) or target_day >= date(2050, 1, 1):
            return Response(status=status.HTTP_400_BAD_REQUEST)
        if not Diary.objects.filter(user=user, date=target_day).exists():
            return Response(status=status.HTTP_404_NOT_FOUND)
        diary = Diary.objects.get(user=user, date=target_day)
        custom_content = request.data.get("customContent", None)
        if custom_content is None:
            return Response(status=status.HTTP_400_BAD_REQUEST)
        custom_content = custom_content[1:-1]
        dict_result = spell_checker.check(custom_content).as_dict()
        tran_custom_content = dict_result["checked"].replace('\\"', '!@!')
        data = {
            "custom_content": tran_custom_content,
            "user": user.id,
            "date": target_day,
        }
        serializer = DiarySerializer(diary, data=data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
