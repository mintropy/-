from django.urls import path

from .views.diary import DiaryViewSet


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


urlpatterns = [
    path("", dairy_list),
    path("<int:diary_id>/", dairy_detail),
]
