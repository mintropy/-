from django.urls import path

from .views.diary import DiaryViewSet
from .views.photo import PhotoViewSet


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
        "post": "create",
    }
)
photo_delete = PhotoViewSet.as_view(
    {
        "post": "delete_photos",
    }
)

urlpatterns = [
    path("", diary_list, name="diary_list"),
    path("<int:year>/<int:month>/", diary_montly),
    path("<int:year>/<int:month>/<int:day>/", diary_daily),
    path("<int:year>/<int:month>/<int:day>/photo/", photo_list),
    path("<int:year>/<int:month>/<int:day>/photo/delete/", photo_delete),
]
