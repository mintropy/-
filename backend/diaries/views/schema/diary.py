from drf_spectacular.utils import extend_schema, OpenApiExample


diary_montly_schema = extend_schema(
    description=(
        "<h1>월간 일기를 조회합니다.</h1>"
        "주어진 year년 month월에 해당하는 일기를 조회합니다<br>"
        "해당 기간에 작성한 일기가 없으면 빈 배열을 반환합니다<br>"
        "날짜의 년, 월을 정수값으로 입력합니다 (ex, 2022/1/, 2022/10/)"
    ),
    summary="월간 일기 조회",
    tags=["일기"],
    examples=[
        OpenApiExample(
            name="list",
            value=[
                {
                    "id": "5286792e-2ec5-44b4-8ebb-7445b717e60b",
                    "photos": "/media/2022/03/16/오늘의 사진.jpg",
                    "content": "test",
                    "date": "2022-03-16",
                    "user": 3,
                },
            ],
            response_only=True,
            status_codes=["200"],
        ),
    ],
)

diary_daily_schema = extend_schema(
    description=(
        "<h1>특정 날짜 일기를 조회합니다.</h1>"
        "주어진 year년 month월 day일에 해당하는 일기를 조회합니다<br>"
        "주어진 날짜에 일기가 없으면 404를 반환합니다<br>"
        "날짜의 년, 월, 일을 정수값으로 입력합니다 (ex, 2022/1/10/, 2022/10/2/)"
    ),
    summary="일간 일기 조회",
    tags=["일기"],
)

diary_create_schema = extend_schema(
    description=("<h1>일기를 작성합니다.</h1>"), summary="일기 작성", tags=["일기"], examples=[]
)

diary_update_schema = extend_schema(
    description=("<h1>특정 날짜의 일기를 수정합니다.</h1>"),
    summary="개별 일기 수정",
    tags=["일기"],
    examples=[],
)

diary_delete_schema = extend_schema(
    description=("<h1>특정 날짜의 일기를 삭제합니다.</h1>"),
    summary="개별 일기 삭제",
    tags=["일기"],
    examples=[],
)
