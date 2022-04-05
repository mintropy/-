import environ
import os
import requests

from back.settings import BASE_DIR


env = environ.Env(
    naver_client_id=(str, "NObDxUOKmxVncvk_L6Jd"),
    naver_client_secret=(str, "L61cxAlFQ7"),
)
environ.Env.read_env(os.path.join(BASE_DIR, ".env"))


def get_translate(text):
    client_id = env("naver_client_id")
    client_secret = env("naver_client_secret")
    data = {"text": text, "source": "en", "target": "ko"}
    url = "https://openapi.naver.com/v1/papago/n2mt"
    header = {"X-Naver-Client-Id": client_id, "X-Naver-Client-Secret": client_secret}
    response = requests.post(url, headers=header, data=data)
    rescode = response.status_code
    if rescode == 200:
        send_data = response.json()
        trans_data = send_data["message"]["result"]["translatedText"]
        return trans_data
    return None
