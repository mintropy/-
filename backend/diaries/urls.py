from django.urls import path
from rest_framework.urlpatterns import format_suffix_patterns
from .views import views

urlpatterns = [
    path('diary/', views.DairyViewSet.as_view()),
    path('diary/<int:pk>/', views.DairyDetailViewSet.as_view()),
]

urlpatterns = format_suffix_patterns(urlpatterns)