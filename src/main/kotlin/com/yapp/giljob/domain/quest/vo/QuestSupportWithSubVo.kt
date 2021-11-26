package com.yapp.giljob.domain.quest.vo

import com.querydsl.core.annotations.QueryProjection
import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.subquest.domain.SubQuest
import com.yapp.giljob.domain.user.domain.User

class QuestSupportWithSubVo @QueryProjection constructor(
    val id: Long,
    val name: String,
    val position: Position,
    val user: User,
    val difficulty: Int,
    val thumbnail: String,
    val subQuestCount: Int,
    val point: Int,
)