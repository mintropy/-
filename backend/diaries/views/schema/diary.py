from drf_spectacular.utils import (
    extend_schema,
    OpenApiResponse
)

from diaries.serializers.diary import DiarySerializer


descriptions = {
    'diary_list_schema': """
    전체 일기를 조회합니다
"""
}


diary_list_schema = extend_schema(
    responses={
        200: OpenApiResponse(
            response=DiarySerializer
        )
    },
    description=descriptions['diary_list_schema'],
    summary='전체 일기 조회',
    tags=['일기'],
    examples=[]
)

diary_retrieve_schema = extend_schema(
    responses={
        200: OpenApiResponse(
            response=DiarySerializer
        )
    },
    description=(
        '개별 일기를 조회합니다.',
    ),
    summary='개별 일기 조회',
    tags=['일기'],
    examples=[]
)
