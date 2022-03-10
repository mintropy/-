from django.urls import path

from .views.diary import DiaryViewSet


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


urlpatterns = [
    path("", diary_list, name='diary_list'),
    path("<str:diary_id>/", diary_detail, name='diary_detail'),
]
