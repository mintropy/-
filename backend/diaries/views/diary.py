from django.shortcuts import get_object_or_404

from rest_framework import status
from rest_framework.response import Response
from rest_framework.viewsets import ViewSet

from ..models import Dairy
from ..serializers.serializers import DairySerializer


class DairyViewSet(ViewSet):
    model = Dairy
    queryset = Dairy.objects.all()

    def list(self, request):
        diaries = Dairy.objects.all()
        serializer = DairySerializer(diaries, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)

    def create(self, request):
        serializer = DairySerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def retrieve(self, request, diary_id):
        diary = get_object_or_404(Dairy, id=diary_id)
        serializer = DairySerializer(diary)
        return Response(serializer.data, status=status.HTTP_200_OK)

    def update(self, request, diary_id):
        diary = get_object_or_404(Dairy, id=diary_id)
        serializer = DairySerializer(diary, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def destroy(self, request, diary_id):
        diary = get_object_or_404(Dairy, id=diary_id)
        diary.delete()
        diaries = Dairy.objects.all()
        serializer = DairySerializer(diaries, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)
