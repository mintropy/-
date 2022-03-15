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
from ..models import Diary
from ..serializers.diary import DiarySerializer


class DiaryViewSet(ViewSet):
    model = Diary
    queryset = Diary.objects.all()
    serializer_class = DiarySerializer

    @diary_list_schema
    def list(self, request):
        diaries = Diary.objects.all()
        serializer = DiarySerializer(diaries, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)

    @diary_create_schema
    def create(self, request):
        serializer = DiarySerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    @diary_retrieve_schema
    def retrieve(self, request, diary_id):
        diary = get_object_or_404(Diary, id=diary_id)
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
    
