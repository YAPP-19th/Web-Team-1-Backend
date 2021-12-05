package com.yapp.giljob.domain.quest.vo

import com.querydsl.core.annotations.QueryProjection
import com.yapp.giljob.domain.quest.domain.Quest

class QuestSupportVo @QueryProjection constructor(
    val quest: Quest,
    val point: Long,
    val participantCount: Long
)