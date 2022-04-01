from django.db import models
from django.contrib.auth.models import AbstractUser


class User(AbstractUser):
    SOCIAL_CHOICES = (
        ("KA", "Kakao"),
        ("GO", "Google"),
    )
    social = models.CharField(max_length=2, choices=SOCIAL_CHOICES, default="KA")
    social_id = models.CharField(max_length=15, null=True, blank=True)
