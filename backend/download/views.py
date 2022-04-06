from rest_framework.viewsets import ViewSet
from django.http import FileResponse

from back.settings import BASE_DIR

import os

class DownloadViewset(ViewSet):
    
    def download(self, request):
        print(BASE_DIR)
        file_dir = os.path.join(".", "urls.py")
        file_dir = open(file_dir, 'r', encoding="utf-8")
        response = FileResponse(file_dir)
        return response
