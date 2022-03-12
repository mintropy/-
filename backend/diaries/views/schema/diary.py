from drf_spectacular.utils import extend_schema


diary_list_schema = extend_schema(
    description=("<h1>전체 일기를 조회합니다.</h1>"), summary="전체 일기 조회", tags=["일기"], examples=[]
)

diary_create_schema = extend_schema(
    description=("<h1>일기를 작성합니다.</h1>"), summary="일기 작성", tags=["일기"], examples=[]
)

diary_retrieve_schema = extend_schema(
    description=("<h1>개별 일기를 조회합니다.</h1>"), summary="개별 일기 조회", tags=["일기"], examples=[]
)

diary_update_schema = extend_schema(
    description=("<h1>개별 일기를 수정합니다.</h1>"), summary="개별 일기 수정", tags=["일기"], examples=[]
)

diary_delete_schema = extend_schema(
    description=("<h1>개별 일기를 삭제합니다.</h1>"), summary="개별 일기 삭제", tags=["일기"], examples=[]
)
