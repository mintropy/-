from drf_spectacular.utils import (
    extend_schema,
    OpenApiResponse
)

from diaries.serializers.photo import PhotoSerializier


descriptions = {
    'photo_list_schema': """
    전체 사진를 조회합니다
"""
}


photo_list_schema = extend_schema(
    responses={
        200: OpenApiResponse(
            response=PhotoSerializier
        )
    },
    description=descriptions['photo_list_schema'],
    summary='전체 사진 조회',
    tags=['사진'],
    examples=[]
)

photo_retrieve_schema = extend_schema(
    responses={
        200: OpenApiResponse(
            response=PhotoSerializier
        )
    },
    description=(
        '개별 사진를 조회합니다.',
    ),
    summary='개별 사진 조회',
    tags=['사진'],
    examples=[]
)
