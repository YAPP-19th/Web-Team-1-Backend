package com.yapp.giljob.domain.quest.dto.response

import com.yapp.giljob.domain.user.dto.response.UserInfoResponseDto
import java.time.LocalDateTime

class QuestReviewResponseDto (
    val review: String,
    val reviewCreatedAt: LocalDateTime,
    val reviewWriter: UserInfoResponseDto
    )