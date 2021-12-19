package com.yapp.giljob.domain.roadmap.vo

import com.querydsl.core.annotations.QueryProjection
import com.yapp.giljob.domain.position.domain.Position

class RoadmapSupportVo @QueryProjection constructor(
    val roadmapId: Long,
    val name: String,
    val position: Position,
    val userId: Long,
    val nickname: String,
    val point: Long
)