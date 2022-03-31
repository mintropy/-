from django.db import models

# Create your models here.
class Dairy(models.Model):
    id = models.IntegerField(primary_key=True)
    users = models.ManyToManyField(
        User,
        related_name='flowers',
    )
    name = models.CharField(max_length=20)
    symbol = models.CharField(max_length=20)

    def __str__(self):
        return f"{self.name}"


class Diary(models.Model):
    def photo_upload_path(instance, filename):
        date_path = timezone.now().strftime("%Y/%m/%d")
        # name = os.path.splitext(filename)[-1].lower()
        return f"{date_path}/{filename}"
    id = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    flower = models.ForeignKey(
        Flower,
        on_delete=models.CASCADE,
        related_name='diaries',
        null=True,
        blank=True,
    )
    user = models.ForeignKey(
        User,
        related_name='diaries',
        on_delete=models.CASCADE
    )
    content = models.CharField(
        max_length=100,
        null=True,
        blank=True,
    )
    custom_content = models.TextField(
        null=True,
        blank=True,
    )
    name = models.CharField(max_length=20)
    sumbol = models.CharField(max_length=20)


class Photo(models.Model):
    id = models.IntegerField(primary_key=True)
    dairies = models.ForeignKey(
        Dairy,
        on_delete=models.CASCADE,
        related_name='photos'
    )
    photo = models.ImageField()
