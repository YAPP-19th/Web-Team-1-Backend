package com.yapp.giljob.domain.roadmap.dto.response

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.user.dto.response.UserSubResponseDto

class RoadmapResponseDto(
    var id: Long,
    var name: String,
    val position: Position,
    val user: UserSubResponseDto
)