package com.yapp.giljob.domain.quest.dto.request

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.subquest.dto.request.SubQuestRequestDto
import com.yapp.giljob.domain.tag.dto.request.TagRequestDto
import org.hibernate.validator.constraints.Range

class QuestSaveRequestDto(
    var name: String,
    @field:Range(min = 0, max = 5)
    var difficulty: Int,
    var position: Position,
    var tagList: List<TagRequestDto> = mutableListOf(),
    var detail: String,
    var thumbnail: String?,
    var subQuestList: List<SubQuestRequestDto> = mutableListOf()
)