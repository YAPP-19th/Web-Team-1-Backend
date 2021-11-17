package com.yapp.giljob.global.common.dto

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.dto.request.QuestSaveRequestDto
import com.yapp.giljob.domain.sign.dto.request.SignInRequestDto
import com.yapp.giljob.domain.sign.dto.request.SignUpRequestDto
import com.yapp.giljob.domain.subquest.dto.SubQuestRequest
import com.yapp.giljob.domain.tag.dto.request.TagRequestDto

class DtoFactory {
    companion object {
        fun testQuestRequest() = QuestSaveRequestDto(
            name = "test quest",
            position = Position.BACKEND,
            tagList = mutableListOf(testTagRequest()),
            difficulty = 1,
            thumbnail = "test.png",
            detail = "test quest detail",
            subQuestList = listOf(SubQuestRequest("sub quest 1"), SubQuestRequest("sub quest 2"))
        )
        fun testTagRequest() = TagRequestDto("tag1")

        fun testSignUpRequest() = SignUpRequestDto(
            kakaoAccessToken = "test",
            position = Position.BACKEND.name,
            nickname = "nickname")
        fun testSignInRequest() = SignInRequestDto(kakaoAccessToken = "test")
    }
}