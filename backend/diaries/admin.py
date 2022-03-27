from django.contrib import admin
from .models import Diary, Flower, Photo


class PhotoInline(admin.TabularInline):
    model = Photo
    extra = 1


@admin.register(Diary)
class DiaryAdmin(admin.ModelAdmin):
    list_display = ('id', 'user', 'date',)
    inlines = (PhotoInline,)


@admin.register(Flower)
class FlowerAdmin(admin.ModelAdmin):
    pass


@admin.register(Photo)
class PhotoAdmin(admin.ModelAdmin):
    pass
