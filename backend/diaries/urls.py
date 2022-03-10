from django.urls import path

from .views.diary import DairyViewSet


dairy_list = DairyViewSet.as_view(
    {
        "get": "list",
        "post": "create",
    }
)
dairy_detail = DairyViewSet.as_view(
    {
        "get": "retrieve",
        "put": "update",
        "delete": "destroy",
    }
)


urlpatterns = [
    path("diary/", dairy_list),
    path("diary/<int:pk>/", dairy_detail),
]
