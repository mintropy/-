from django.shortcuts import get_object_or_404

from rest_framework import status
from rest_framework.response import Response
from rest_framework.viewsets import ViewSet

from ..models import Flower
from ..serializers.flower import FlowerSerializer
from accounts.views.user import get_kakao_user_info
from accounts.models import User


class FlowerViewSet(ViewSet):
    model = Flower
    queryset = Flower.objects.all()
    serializer_class = FlowerSerializer
    
    def list(self, request):
        flowers = Flower.objects.all()
        serializer = FlowerSerializer(flowers, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)
    
    def retrieve(self, request, flower_id):
        flower = get_object_or_404(Flower, id=flower_id)
        serialiezr = FlowerSerializer(flower)
        return Response(serialiezr.data, status=status.HTTP_200_OK)
    
    def user(self, request):
        token = request.headers.get("Authorization", "")
        user = get_kakao_user_info(token)        
        flowers = user.flowers.all()
        serializer = FlowerSerializer(flowers, many=True)
        return Response(serializer.data)
