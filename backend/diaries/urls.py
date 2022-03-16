from django.urls import path

from .views.diary import DiaryViewSet
from .views.photo import PhotoViewSet


diary_list = DiaryViewSet.as_view(
    {
        "post": "create",
    }
)
diary_detail = DiaryViewSet.as_view(
    {
        "put": "update",
        "delete": "destroy",
    }
)
diary_montly = DiaryViewSet.as_view({'get': 'montly'})
diary_daily = DiaryViewSet.as_view({'get': 'daily'})

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
    path("<int:year>/<int:month>/", diary_montly),
    path("<int:year>/<int:month>/<int:day>/", diary_daily),
    
    path("", diary_list, name="diary_list"),
    path("<str:diary_id>/", diary_detail, name="diary_detail"),
    path("dairy/<str:diary_id>/photo/", photo_list),
    path("dairy/<str:diary_id>/photo/<str:photo_id>/", photo_detail),
]
