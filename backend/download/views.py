import os
import mimetypes

from django.http import HttpResponse

from rest_framework.viewsets import ViewSet
from wsgiref.util import FileWrapper

from back.settings import BASE_DIR


class DownloadViewset(ViewSet):
    def send_file2(self, request):
        """
        Send a file through Django without loading the whole file into
        memory at once. The FileWrapper will turn the file object into an
        iterator for chunks of 8KB.
        """
        file_name = "app.apk"
        filePath = os.path.join(BASE_DIR, "download", file_name)
        file = open(filePath, "rb")
        wrapper = FileWrapper(file)
        mime_type, _ = mimetypes.guess_type(filePath)
        response = HttpResponse(wrapper, content_type=mime_type)
        response["Content-Disposition"] = "attachment; filename=%s" % file_name
        response["Content-Length"] = os.path.getsize(filePath)
        return response
