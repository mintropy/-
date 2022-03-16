from rest_framework import serializers

from ..serializers.photo import PhotoSerializier

from ..models import Diary


class DiarySerializer(serializers.ModelSerializer):
    photos = PhotoSerializier(many=True, read_only=True)
    
    class Meta:
        model = Diary
        fields = '__all__'
 