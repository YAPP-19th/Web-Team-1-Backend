package com.yapp.giljob.domain.quest.vo

import com.querydsl.core.annotations.QueryProjection
import com.yapp.giljob.domain.position.domain.Position

class QuestPositionCountVo @QueryProjection constructor(
    val position: Position,
    val questCount: Long
)