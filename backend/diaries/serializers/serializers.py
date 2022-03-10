from rest_framework import serializers
from ..models import Dairy, Flower, Photo


class DairySerializer(serializers.ModelSerializer):
    class Meta:
        model = Dairy
        fields = '__all__'
        
class FlowerSerializer(serializers.ModelSerializer):
    
    class Meta:
        model = Flower
        fields = '__all__'        
        
class PhotoSerialzier(serializers.ModelSerializer):
    
    class Meta:
        model = Photo
        fields = '__all__'