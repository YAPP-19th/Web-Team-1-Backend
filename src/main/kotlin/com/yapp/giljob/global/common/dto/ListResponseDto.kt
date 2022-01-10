package com.yapp.giljob.global.common.dto

class ListResponseDto<T>(
    var totalCount: Long,
    var contentList: List<T>
)