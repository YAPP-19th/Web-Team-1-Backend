package com.yapp.giljob.domain.quest.dto.request

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.subquest.dto.request.SubQuestRequestDto
import com.yapp.giljob.domain.tag.dto.response.TagResponseDto

class QuestSaveRequestDto(
    var name: String,
    var difficulty: Int,
    var position: Position,
    var tagList: List<TagResponseDto> = mutableListOf(),
    var detail: String,
    var thumbnail: String,
    var subQuestList: List<SubQuestRequestDto> = mutableListOf()
)