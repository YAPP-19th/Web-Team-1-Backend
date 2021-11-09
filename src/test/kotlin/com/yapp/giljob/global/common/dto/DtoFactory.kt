package com.yapp.giljob.global.common.dto

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.dto.QuestRequest
import com.yapp.giljob.domain.subquest.dto.SubQuestRequest
import com.yapp.giljob.domain.tag.dto.TagRequest

class DtoFactory {
    companion object {
        fun teatQuestRequest() = QuestRequest(
            name = "test quest",
            position = Position.BACKEND,
            tagList = listOf(TagRequest("tag1"), TagRequest("tag2")),
            difficulty = 1,
            thumbnail = "test.png",
            detail = "test quest detail",
            subQuestList = listOf(SubQuestRequest("sub quest 1"), SubQuestRequest("sub quest 2"))
        )
        fun teatTagRequest() = TagRequest("tag1")
    }
}