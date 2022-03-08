from rest_framework.test import APITestCase
import factory


class TestSetUp(APITestCase):
    @classmethod
    def setUpTestData(cls):
        content_type = 'application/json'
