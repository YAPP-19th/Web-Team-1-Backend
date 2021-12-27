package com.yapp.giljob.domain.roadmap.vo

import com.querydsl.core.annotations.QueryProjection
import com.yapp.giljob.domain.roadmap.domain.Roadmap

class RoadmapSupportVo @QueryProjection constructor(
    val roadmap: Roadmap,
    val point: Long
)