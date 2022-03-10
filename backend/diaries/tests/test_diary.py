from datetime import date

from django.urls import reverse
from rest_framework import status

from .test_setup import TestSetUp


class TestDiary(TestSetUp):

    def setUp(self) -> None:
        return super().setUp()

    def test_diary_list(self):
        url = reverse('diary_list')
        res = self.client.get(url)
        self.assertEqual(res.status_code, status.HTTP_200_OK)
        today = date.today()
        self.assertEqual(res.data[0]['date'], today.strftime("%Y-%m-%d"))

    def test_diary_create(self):
        url = reverse('diary_list')
        data = {}
        res = self.client.post(
            url,
            data=data,
            content_type=self.content_type
        )
        self.assertEqual(res.status_code, status.HTTP_201_CREATED)

    def test_diary_update(self):
        url = reverse('diary_list')
        data = {}
        res = self.client.post(
            url,
            data=data,
            content_type=self.content_type
        )
        id = res.data['id']
        url = reverse('diary_detail', kwargs={'diary_id': id})
        data = {}
        res = self.client.put(
            url,
            data=data,
            content_type=self.content_type
        )
        self.assertEqual(res.status_code, status.HTTP_201_CREATED)

    def test_diary_delete(self):
        url = reverse('diary_list')
        data = {}
        res = self.client.post(
            url,
            data=data,
            content_type=self.content_type
        )
        id = res.data['id']
        url = reverse('diary_detail', kwargs={'diary_id': id})
        res = self.client.delete(url)
        self.assertEqual(res.status_code, status.HTTP_200_OK)
