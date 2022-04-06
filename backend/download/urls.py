from django.urls import path
from .views import DownloadViewset

download = DownloadViewset.as_view({"get": "download"})


urlpatterns = [
    path("", download),
]
