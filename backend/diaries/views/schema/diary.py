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
                    "flower": "1",
                    "date": "2022-04-01"
                },
                {
                    "flower": "22",
                    "date": "2022-04-03"
                },
                {
                    "flower": "14",
                    "date": "2022-04-04"
                }
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
    examples=[
        OpenApiExample(
            name="list",
            value=[
                {
                    "id": "a9a12015-1b0e-4fbb-b70e-cb1c8e630b25",
                    "koContent": "도구와 베개 몇 개",
                    "enContent": "some tools and some pillows",
                    "customContent": "null",
                    "date": "2022-04-04",
                    "photo": "/media/2022/04/04/KakaoTalk_20220331_185934692_02.jpg",
                    "flower": "1",
                    "user": "2"
                }
            ],
            response_only=True,
            status_codes=["200"],
        ),
    ],
)

diary_create_schema = extend_schema(
    description=(
        "<h1>일기를 작성합니다.</h1>"
        "주어진 year년 month월 day일에 해당하는 일기를 작성합니다<br>"
        "날짜 범위를 벗어나면 400를 반환합니다<br>"
        "날짜의 년, 월, 일을 정수값으로 입력합니다 (ex, 2022/1/10/, 2022/10/2/)<br>"
        "사진 파일을 업로드합니다<br>"
        "이미 photo와 content가 있는 경우, 교체합니다"
    ), 
    summary="일기 작성", 
    tags=["일기"], 
    examples=[
        OpenApiExample(
            name="request",
            value=[
                {
                    "date": "2022-04-04",
                    "photo": "/media/2022/04/04/KakaoTalk_20220331_185934692_02.jpg",
                }
            ],
            response_only=True,
            status_codes=["200"],
        ),
    ]
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
