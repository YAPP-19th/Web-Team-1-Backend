package com.yapp.giljob.domain.roadmap.dto.request

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.dto.request.QuestRequestDto

class RoadmapSaveRequestDto (
    var name: String,
    var questList: List<QuestRequestDto> = mutableListOf(),
    val position: Position
)