from django.contrib import admin
from django.contrib.auth.admin import UserAdmin
from .models import User


@admin.register(User)
class CustomUserAdmin(UserAdmin):
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
