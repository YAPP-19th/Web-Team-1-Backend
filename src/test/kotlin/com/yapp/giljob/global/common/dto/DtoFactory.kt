package com.yapp.giljob.global.common.dto

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.dto.QuestRequest
import com.yapp.giljob.domain.quest.dto.QuestResponse
import com.yapp.giljob.domain.subquest.dto.SubQuestRequest
import com.yapp.giljob.domain.tag.dto.TagRequest

class DtoFactory {
    companion object {
        fun testQuestRequest() = QuestRequest(
            name = "test quest",
            position = Position.BACKEND,
            tagList = mutableListOf(testTagRequest()),
            difficulty = 1,
            thumbnail = "test.png",
            detail = "test quest detail",
            subQuestList = listOf(SubQuestRequest("sub quest 1"), SubQuestRequest("sub quest 2"))
        )

        fun testTagRequest() = TagRequest("tag1")
        fun testQuestResponse() = QuestResponse(
            id = 1L,
            name = "test quest",
            position = Position.BACKEND,
            difficulty = 1,
            thumbnail = "test.png",
            user = QuestResponse.UserDto(
                id = 1L,
                nickname = "testNickname"
            )
        )
    }
}