from rest_framework.test import APITestCase
import factory

from diaries.models import Diary, Flower, Photo


class DiaryFactory(factory.Factory):
    class Meta:
        model = Diary

    id = factory.Sequence(lambda n: n)
    date = factory.Faker('date_object')


class TestSetUp(APITestCase):
    @classmethod
    def setUpTestData(cls):
        cls.content_type = 'application/json'

        cls.diary = DiaryFactory()
        cls.diary.save()
