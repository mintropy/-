from django.contrib import admin
from django.contrib.auth.admin import UserAdmin

from .models import User
from diaries.models import Flower


class FlowerInline(admin.TabularInline):
    model = Flower.users.through
    extra = 1


@admin.register(User)
class CustomUserAdmin(UserAdmin):
    list_display = (
        "id",
        "username",
        "flowers_count",
    )
    ordering = ("id",)
    fieldsets = UserAdmin.fieldsets + (
        (
            "social",
            {
                "fields": (
                    "social",
                    "social_id",
                )
            },
        ),
    )
    inlines = (FlowerInline,)

    @admin.display()
    def flowers_count(self, obj):
        return obj.flowers.count()
