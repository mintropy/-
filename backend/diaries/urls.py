from django.urls import path

from .views.diary import DiaryViewSet
from .views.photo import PhotoViewSet


diary_list = DiaryViewSet.as_view(
    {
        "post": "create",
    }
)
diary_montly = DiaryViewSet.as_view({
    'get': 'montly'
})
diary_daily = DiaryViewSet.as_view({
    'get': 'daily',
    'put': 'update',
    'delete': 'destroy',
})

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
    path("<int:year>/<int:month>/", diary_montly),
    path("<int:year>/<int:month>/<int:day>/", diary_daily),
    
    path("dairy/<str:diary_id>/photo/", photo_list),
    path("dairy/<str:diary_id>/photo/<str:photo_id>/", photo_detail),
]
