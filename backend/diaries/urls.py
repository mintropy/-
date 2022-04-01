from django.urls import path

from .views.diary import DiaryViewSet
from .views.photo import PhotoViewSet
from .views.flower import FlowerViewSet


diary_list = DiaryViewSet.as_view(
    {
        "post": "create",
    }
)
diary_montly = DiaryViewSet.as_view({"get": "montly"})
diary_daily = DiaryViewSet.as_view(
    {
        "get": "daily",
        "put": "update",
        "delete": "destroy",
    }
)

photo_list = PhotoViewSet.as_view(
    { 
        "get": "list",
        "post": "create",
    }
)
photo_detail = PhotoViewSet.as_view(
    {
        "get": "retrieve",
        "delete": "destroy",
    }
)

flower_list = FlowerViewSet.as_view({"get": "list"})
flower_detail = FlowerViewSet.as_view({"get": "retrieve"})

urlpatterns = [
    path("", diary_list, name="diary_list"),
    path("<int:year>/<int:month>/", diary_montly),
    path("<int:year>/<int:month>/<int:day>/", diary_daily),
    path("photo/", photo_list),
    path("photo/<str:photo_id>/", photo_detail),
    path("flowers/", flower_list),
    path("flowers/<int:flower_id>/", flower_detail),
]
