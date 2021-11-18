package com.yapp.giljob.domain.quest.dto.request

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.subquest.dto.SubQuestRequest
import com.yapp.giljob.domain.tag.dto.request.TagRequestDto

class QuestSaveRequestDto(
    var name: String,
    var difficulty: Int,
    var position: Position,
    var tagList: List<TagRequestDto> = mutableListOf(),
    var detail: String,
    var thumbnail: String,
    var subQuestList: List<SubQuestRequest> = mutableListOf()
)