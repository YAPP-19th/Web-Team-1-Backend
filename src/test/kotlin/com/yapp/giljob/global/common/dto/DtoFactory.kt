package com.yapp.giljob.global.common.dto

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.dto.response.QuestResponseDto
import com.yapp.giljob.domain.quest.dto.request.QuestSaveRequestDto
import com.yapp.giljob.domain.sign.dto.request.SignInRequestDto
import com.yapp.giljob.domain.sign.dto.request.SignUpRequestDto
import com.yapp.giljob.domain.subquest.dto.SubQuestRequest
import com.yapp.giljob.domain.tag.dto.request.TagRequestDto
import com.yapp.giljob.domain.user.dto.UserSubDto

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

        fun testQuestResponse() = QuestResponseDto(
            id = 1L,
            name = "test quest",
            position = Position.BACKEND,
            difficulty = 1,
            thumbnail = "test.png",
            user = UserSubDto(
                id = 1L,
                nickname = "testNickname",
                point = 100
            )
        )

        fun testTagRequest() = TagRequestDto("tag1")

        fun testSignUpRequest() = SignUpRequestDto(
            kakaoAccessToken = "test",
            position = Position.BACKEND.name,
            nickname = "nickname")
        fun testSignInRequest() = SignInRequestDto(kakaoAccessToken = "test")
    }
}