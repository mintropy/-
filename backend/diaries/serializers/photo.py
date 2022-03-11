from rest_framework import serializers
from ..models import Photo


class PhotoSerializier(serializers.ModelSerializer):

    class Meta:
        model = Photo
        fields = '__all__'
