from django.contrib import admin
from .models import Diary, Flower, Photo


@admin.register(Diary)
class DiaryAdmin(admin.ModelAdmin):
    list_display = ('user', 'date',)
    pass


@admin.register(Flower)
class FlowerAdmin(admin.ModelAdmin):
    pass


@admin.register(Photo)
class PhotoAdmin(admin.ModelAdmin):
    pass
