from django.db import models

# Create your models here.
class Dairy(models.Model):
    id = models.IntegerField(primary_key=True)
    content = models.CharField(
        max_length=100,
        null=True,
        blank=True,
    )
    date = models.DateField(auto_now=True)


class Flower(models.Model):
    id = models.IntegerField(primary_key=True)
    dairies = models.ForeignKey(
        Dairy,
        on_delete=models.SET_NULL,
        related_name='flower'
    )
    name = models.CharField()
    sumbol = models.CharField()


class Photo(models.Model):
    id = models.IntegerField(primary_key=True)
    dairies = models.ForeignKey(
        Dairy,
        on_delete=models.SET_NULL,
        related_name='photos'
    )
    photo = models.ImageField()
