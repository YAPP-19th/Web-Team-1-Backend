package com.yapp.giljob.domain.quest.dto

import com.querydsl.core.annotations.QueryProjection
import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.user.domain.User

class QuestSupportDto @QueryProjection constructor(
    val id: Long,
    val name: String,
    val position: Position,
    val user: User,
    val difficulty: Int,
    val point: Int,
    val thumbnail: String
)