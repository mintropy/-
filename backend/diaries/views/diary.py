from datetime import date

from django.shortcuts import get_object_or_404

from rest_framework import status
from rest_framework.response import Response
from rest_framework.viewsets import ViewSet

from .schema.diary import (
    diary_list_schema,
    diary_create_schema,
    diary_retrieve_schema,
    diary_update_schema,
    diary_delete_schema,
)
from ..models import Diary, Photo
from ..serializers.diary import DiarySerializer
from accounts.views.user import get_kakao_user_info
from accounts.models import User


class DiaryViewSet(ViewSet):
    model = Diary
    queryset = Diary.objects.all()
    serializer_class = DiarySerializer

    @diary_list_schema
    def list(self, request):
        token = request.headers.get('Authorization', '')
        user_info = get_kakao_user_info(token)
        if not user_info:
            return Response(status=status.HTTP_401_UNAUTHORIZED)
        user_id = user_info.get('id', None)
        user = get_object_or_404(User, social_id=user_id)
        diaries = Diary.objects.filter(user_id=user.id)
        serializer = DiarySerializer(diaries, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)

    @diary_create_schema
    def create(self, request):
        token = request.headers.get('Authorization', '')
        user_info = get_kakao_user_info(token)
        if not user_info:
            return Response(status=status.HTTP_401_UNAUTHORIZED)
        user_id = user_info.get('id', None)
        user = get_object_or_404(User, social_id=user_id)
        
        if Diary.objects.filter(user=user, date=date.today()).exists():
            return Response(status=status.HTTP_400_BAD_REQUEST)
        diary = Diary.objects.create(user=user, date=date.today())
        photos = [
            {
                'diaries': diary.id,
                'files': photo
            }
            for photo in request.FILES.values()
        ]
        data = {
            'content': request.data.get('content', None),
            'user': user.id,
            'photos': photos
        }
        serializer = DiarySerializer(data=data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    @diary_retrieve_schema
    def retrieve(self, request, diary_id):
        token = request.headers.get('Authorization', '')
        user_info = get_kakao_user_info(token)
        if not user_info:
            return Response(status=status.HTTP_401_UNAUTHORIZED)
        user_id = user_info.get('id', None)
        user = get_object_or_404(User, social_id=user_id)
        diary = get_object_or_404(Diary, user_id=user.id, id=diary_id)
        serializer = DiarySerializer(diary)
        return Response(serializer.data, status=status.HTTP_200_OK)

    @diary_update_schema
    def update(self, request, diary_id):
        diary = get_object_or_404(Diary, id=diary_id)
        serializer = DiarySerializer(diary, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    @diary_delete_schema
    def destroy(self, request, diary_id):
        diary = get_object_or_404(Diary, id=diary_id)
        diary.delete()
        diaries = Diary.objects.all()
        serializer = DiarySerializer(diaries, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)
    
