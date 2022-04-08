import uuid

from django.db import models
from django.utils import timezone

from accounts.models import User


class Flower(models.Model):
    id = models.IntegerField(primary_key=True)
    users = models.ManyToManyField(
        User,
        related_name="flowers",
    )
    name = models.CharField(unique=True, max_length=20)
    symbol = models.CharField(max_length=20)

    def __str__(self):
        return f"{self.name}"


class Diary(models.Model):
    def photo_upload_path(instance, filename):
        date_path = instance.date.strftime("%Y/%m/%d")
        return f"{date_path}/{filename}"

    id = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    flower = models.ForeignKey(
        Flower,
        on_delete=models.CASCADE,
        related_name="diaries",
        null=True,
        blank=True,
    )
    user = models.ForeignKey(User, related_name="diaries", on_delete=models.CASCADE)
    ko_content = models.CharField(
        max_length=100,
        null=True,
        blank=True,
    )
    en_content = models.CharField(
        max_length=100,
        null=True,
        blank=True,
    )
    custom_content = models.TextField(
        null=True,
        blank=True,
    )
    date = models.DateField()
    photo = models.ImageField(
        upload_to=photo_upload_path,
        null=True,
        blank=True,
    )

    def __str__(self):
        return f"{self.user} {self.date}"


class Photo(models.Model):
    def photo_upload_path(instance, filename):
        date_path = timezone.now().strftime("%Y/%m/%d")
        return f"{date_path}/{filename}"

    id = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    dairies = models.ForeignKey(Diary, on_delete=models.CASCADE, related_name="photos")
    photo = models.ImageField(
        upload_to=photo_upload_path,
        null=True,
        blank=True,
    )
