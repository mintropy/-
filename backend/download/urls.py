from django.urls import path
from .views import DownloadViewset

download = DownloadViewset.as_view({"get": "send_file2"})


urlpatterns = [
    path("", download),
]
