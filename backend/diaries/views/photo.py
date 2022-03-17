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
from accounts.views.user import get_kakao_user_info
from accounts.models import User


class PhotoViewSet(ViewSet):
    model = Photo
    queryset = Diary.objects.all()
    serializer_class = PhotoSerializier

    @photo_create_schema
    def create(self, request, year, month, day):
        token = request.headers.get("Authorization", "")
        user_info = get_kakao_user_info(token)
        if not user_info:
            return Response(status=status.HTTP_401_UNAUTHORIZED)
        user_id = user_info.get("id", None)
        user = get_object_or_404(User, social_id=user_id)

        target_day = date(year, month, day)
        diary = get_object_or_404(Diary, user=user, date=target_day)
        response = []
        for photo in request.FILES.values():
            data = {
                'dairies': diary.id,
                'photo': photo,
            }
            serializer = PhotoSerializier(data=data)
            if serializer.is_valid():
                serializer.save()
                response.append(serializer.data)
        return Response(response, status.HTTP_201_CREATED)

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
