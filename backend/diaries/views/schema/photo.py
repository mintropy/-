from drf_spectacular.utils import extend_schema


photo_list_schema = extend_schema(
    description=("<h1>전체 사진을 조회합니다.</h1>"), 
    summary="전체 사진 조회", 
    tags=["사진"], 
    examples=[]
)

photo_create_schema = extend_schema(
    description=("<h1>사진을 등록하거나 수정합니다</h1>"),
    summary="사진 등록 또는 수정",
    tags=["사진"],
    examples=[],
)

photo_retrieve_schema = extend_schema(
    description=("<h1>개별 사진를 조회합니다.</h1>",),
    summary="개별 사진 조회",
    tags=["사진"],
    examples=[],
)

photo_destroy_schema = extend_schema(
    description=("<h1>사진을 삭제합니다.</h1>",), summary="개별 사진 삭제", tags=["사진"], examples=[]
)
