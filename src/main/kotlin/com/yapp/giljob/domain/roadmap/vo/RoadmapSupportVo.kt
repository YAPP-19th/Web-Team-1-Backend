package com.yapp.giljob.domain.roadmap.vo

import com.querydsl.core.annotations.QueryProjection
import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.roadmap.domain.Roadmap
import com.yapp.giljob.domain.user.domain.User

class RoadmapSupportVo @QueryProjection constructor(
    val roadmap: Roadmap,
    val user: User,
    val point: Long
)