from drf_spectacular.utils import (
    extend_schema,
    OpenApiResponse
)

from diaries.serializers.diary import DiarySerializer


diary_list_schema = extend_schema(
    responses={
        200: OpenApiResponse(
            response=DiarySerializer
        )
    },
    description=(
        '전체 일기를 조회합니다.',
    ),
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
