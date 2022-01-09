package com.yapp.giljob.domain.quest.dto.response

class QuestResponseDto<T>(
    var totalCount: Long,
    var questList: List<T>
)