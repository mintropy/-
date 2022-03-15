from drf_spectacular.utils import (
    extend_schema
)


kakao_login_schema = extend_schema(
    description=(
        '카카로 소셜 로그인합니다.'
    ),
    summary='카카오 소셜 로그인',
    tags=['소셜 로그인'],
)

kakao_user_info_schema = extend_schema(
    description=(
        '카카로 소셜 정보를 가져옵니다.'
    ),
    summary='카카오 소셜 정보',
    tags=['소셜 로그인'],
)

kakao_unlink_schema = extend_schema(
    description=(
        '카카로 소셜 연결을 해제합니다'
    ),
    summary='카카오 소셜 연결 해제',
    tags=['소셜 로그인'],
)
