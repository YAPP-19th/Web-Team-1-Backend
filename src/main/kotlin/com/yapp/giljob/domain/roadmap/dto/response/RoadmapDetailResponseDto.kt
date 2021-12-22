package com.yapp.giljob.domain.roadmap.dto.response

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.dto.QuestDto
import com.yapp.giljob.domain.user.dto.response.UserInfoResponseDto

class RoadmapDetailResponseDto(
    val name: String,
    val writer: UserInfoResponseDto,
    val position: Position,
    val questList: List<QuestDto>,
    val isScraped: Boolean
)