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
        related_name='flower',
        null=True
    )
    name = models.CharField(max_length=20)
    sumbol = models.CharField(max_length=20)


class Photo(models.Model):
    def photo_upload_path(self, instance, filename):
        return f'submission/{instance.photo.id}/{filename}'
    
    id = models.IntegerField(primary_key=True)
    dairies = models.ForeignKey(
        Dairy,
        on_delete=models.CASCADE,
        related_name='photos'
    )
    photo = models.ImageField(
        upload_to=photo_upload_path,
        null=True,
        blank=True,
    )
