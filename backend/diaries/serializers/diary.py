from rest_framework import serializers

from ..models import Diary


class DiarySerializer(serializers.ModelSerializer):

    class Meta:
        model = Diary
        fields = "__all__"
        excludes = ('photos',)

class MonthDiarySerializer(serializers.ModelSerializer):
    
    class Meta:
        model = Diary
        fields = (
            'flower', 'date', 
        )
        
