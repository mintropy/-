from django.urls import path

from .views.diary import DiaryViewSet
from .views.photo import PhotoViewSet

dairy_list = DiaryViewSet.as_view(
    {
        "get": "list",
        "post": "create",
    }
)
dairy_detail = DiaryViewSet.as_view(
    {
        "get": "retrieve",
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


urlpatterns = [
    #일기
    path("dairy/", dairy_list),
    path("dairy/<str:diary_id>/", dairy_detail),
    #사진(업로드)
    path("dairy/<str:diary_id>/photo/", photo_list),
    path("dairy/<str:diary_id>/photo/<str:photo_id>/", photo_detail),
]
