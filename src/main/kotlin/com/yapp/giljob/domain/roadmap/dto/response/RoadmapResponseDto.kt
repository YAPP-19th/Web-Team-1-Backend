package com.yapp.giljob.domain.roadmap.dto.response

import com.yapp.giljob.domain.position.domain.Position

class RoadmapResponseDto(
    val id: Long,
    val name: String,
    val position: Position,
    val userId: Long,
    val nickname: String,
    val point: Long
)