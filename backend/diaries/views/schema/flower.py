from drf_spectacular.utils import extend_schema, OpenApiExample


flower_user_schema = extend_schema(
    description=(
        "<h1>유저가 가진 꽃을 조회합니다.</h1>"
    ),
    summary="유저 꽃 목록 조회",
    tags=["꽃",],
    examples=[
        OpenApiExample(
            name="flower list",
            value=[
                {
                    "id": 1,
                    "name": "빨강 국화",
                    "symbol": "나는 당신을 사랑합니다"
                },
                {
                    "id": 2,
                    "name": "보라 국화",
                    "symbol": "내 모든것을 그대에게"
                },
            ],
            response_only=True,
            status_codes=["200"],
        )
    ]
)
