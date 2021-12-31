package com.yapp.giljob.domain.quest.dto.response

class QuestReviewWithTotalCountResponseDto (
    val totalReviewCount: Long,
    val reviewList: List<QuestReviewResponseDto>
    )