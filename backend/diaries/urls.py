from django.urls import path

from .views.diary import DiaryViewSet
from .views.photo import PhotoViewSet


diary_list = DiaryViewSet.as_view(
    {
        "get": "list",
        "post": "create",
    }
)
diary_detail = DiaryViewSet.as_view(
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
    path("", diary_list, name="diary_list"),
    path("<str:diary_id>/", diary_detail, name="diary_detail"),
    path("dairy/<str:diary_id>/photo/", photo_list),
    path("dairy/<str:diary_id>/photo/<str:photo_id>/", photo_detail),
]
