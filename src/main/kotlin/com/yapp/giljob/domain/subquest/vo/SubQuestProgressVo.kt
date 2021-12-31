package com.yapp.giljob.domain.subquest.vo

import com.querydsl.core.annotations.QueryProjection

class SubQuestProgressVo @QueryProjection constructor(
    val subQuestId: Long,
    val subQuestName: String,
    val isCompleted: Boolean
)