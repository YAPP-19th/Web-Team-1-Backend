package com.yapp.giljob.domain.roadmap.dto.response

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.dto.QuestDto
import com.yapp.giljob.domain.user.dto.response.UserInfoResponseDto

class RoadmapResponseDto(
    val name: String,
    val user: UserInfoResponseDto,
    val position: Position,
    val questList: List<QuestDto>,
    val isScraped: Boolean
)