from django.contrib import admin
from .models import Diary, Flower, Photo


class PhotoInline(admin.TabularInline):
    model = Photo
    extra = 1


@admin.register(Diary)
class DiaryAdmin(admin.ModelAdmin):
    list_display = ('user', 'date', 'custom_content', 'flower',)
    inlines = (PhotoInline,)
    ordering = ('-date',)


@admin.register(Flower)
class FlowerAdmin(admin.ModelAdmin):
    list_display = ('id', 'name', 'symbol',)


@admin.register(Photo)
class PhotoAdmin(admin.ModelAdmin):
    list_display = ('dairies', 'photo',)
