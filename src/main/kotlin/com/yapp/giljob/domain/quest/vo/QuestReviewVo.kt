package com.yapp.giljob.domain.quest.vo

import com.querydsl.core.annotations.QueryProjection
import com.yapp.giljob.domain.user.domain.User
import java.time.LocalDateTime

class QuestReviewVo @QueryProjection constructor(
    val review: String,
    val reviewCreatedAt: LocalDateTime,
    val reviewWriter: User,
    val reviewWriterPoint: Long
)