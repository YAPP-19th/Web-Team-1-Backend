package com.yapp.giljob.domain.quest.dto

import com.yapp.giljob.domain.position.domain.Position

class QuestConditionDto(
    val position: Position,
    val userId: Long? = null,
    val keyword: String? = null,
)