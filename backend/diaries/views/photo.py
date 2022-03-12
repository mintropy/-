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


class PhotoViewSet(ViewSet):
    model = Photo
    queryset = Diary.objects.all()
    serializer_class = PhotoSerializier
    
    @photo_list_schema
    def list(self, request, diary_id):
        photo = Photo.objects.filter(dairies=diary_id)
        serializer = PhotoSerializier(photo, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)

    @photo_create_schema
    def create(self, request, diary_id):
        if not Diary.objects.filter(pk=diary_id).exists():
            return Response(
                    status=status.HTTP_404_NOT_FOUND
                )
        data={
            'id': request.data.get('id', None),
            'photo': request.FILES.get('photo', None),
            'diaries': diary_id,   
        }
        photo = Photo.objects.filter(dairies=diary_id)
        if photo:
            serializer = PhotoSerializier(data=data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
    
    @photo_retrieve_schema
    def retrieve(self, request, diary_id):
        if not Diary.objects.filter(pk=diary_id).exists():
            return Response(
                    status=status.HTTP_404_NOT_FOUND
                )
        photo = Photo.objects.filter(dairies=diary_id)
        serializer = PhotoSerializier(photo)
        return Response(serializer.data, status=status.HTTP_200_OK)

    # def update(self, request, photo_id):
    #     photo = get_object_or_404(Photo, id=photo_id)
    #     serializer = PhotoSerializer(photo, data=request.data)
    #     if serializer.is_valid():
    #         serializer.save()
    #         return Response(serializer.data, status=status.HTTP_201_CREATED)
    #     return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    @photo_destroy_schema
    def destroy(self, request, diary_id):
        if not Diary.objects.filter(pk=diary_id).exists():
            return Response(
                    status=status.HTTP_404_NOT_FOUND
                )
        photo = Photo.objects.filter(dairies=diary_id)    
        photo.delete()
        return Response(status=status.HTTP_200_OK)
    
    