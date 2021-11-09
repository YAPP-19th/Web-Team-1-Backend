package com.yapp.giljob.domain.quest.dto

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.subquest.dto.SubQuestRequest
import com.yapp.giljob.domain.tag.dto.TagRequest

class QuestRequest(
    var name: String?,
    var difficulty: Int?,
    var position: Position?,
    var tagList: List<TagRequest> = mutableListOf(),
    var detail: String?,
    var thumbnail: String?,
    var subQuestList: List<SubQuestRequest> = mutableListOf()
)