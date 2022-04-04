from django.contrib import admin
from .models import Diary, Flower


@admin.register(Diary)
class DiaryAdmin(admin.ModelAdmin):
    list_display = ('user', 'date', 'custom_content', 'flower',)
    ordering = ('-date',)


@admin.register(Flower)
class FlowerAdmin(admin.ModelAdmin):
    list_display = ('id', 'name', 'symbol',)
    filter_horizontal  = ('users',)
