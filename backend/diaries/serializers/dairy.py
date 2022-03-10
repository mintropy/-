from rest_framework import serializers
from ..models import Dairy


class DairySerializer(serializers.ModelSerializer):

    class Meta:
        model = Dairy
        fields = '__all__'
