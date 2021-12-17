package com.yapp.giljob.domain.roadmap.dto.response

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.dto.QuestDto

class RoadmapResponseDto(
    val name: String,
    val position: Position,
    val questList: List<QuestDto>,
    val isScraped: Boolean
)