from django.contrib import admin
from .models import Diary, Flower


@admin.register(Diary)
class DiaryAdmin(admin.ModelAdmin):
    list_display = (
        "user",
        "date",
        "flower",
        "is_photo",
        "is_ko_content",
        "is_en_content",
    )
    ordering = ("-date",)
    list_filter = ("user",)

    @admin.display(
        boolean=True,
    )
    def is_photo(self, obj):
        if obj.photo is None:
            return False
        return True

    @admin.display(
        boolean=True,
    )
    def is_ko_content(self, obj):
        if obj.ko_content is None:
            return False
        return True

    @admin.display(
        boolean=True,
    )
    def is_en_content(self, obj):
        if obj.en_content is None:
            return False
        return True


@admin.register(Flower)
class FlowerAdmin(admin.ModelAdmin):
    list_display = (
        "id",
        "name",
        "symbol",
        "count_user",
    )
    ordering = ("id",)
    list_display_links = ("name",)
    filter_horizontal = ("users",)

    @admin.display()
    def count_user(self, obj):
        return obj.users.count()
