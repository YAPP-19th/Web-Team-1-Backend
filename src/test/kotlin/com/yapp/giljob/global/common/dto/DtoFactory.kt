package com.yapp.giljob.global.common.dto

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.dto.response.QuestResponseDto
import com.yapp.giljob.domain.quest.dto.request.QuestSaveRequestDto
import com.yapp.giljob.domain.quest.dto.response.QuestByParticipantResponseDto
import com.yapp.giljob.domain.sign.dto.request.SignInRequestDto
import com.yapp.giljob.domain.sign.dto.request.SignUpRequestDto
import com.yapp.giljob.domain.tag.dto.request.TagRequestDto
import com.yapp.giljob.domain.tag.dto.response.TagResponseDto
import com.yapp.giljob.domain.user.vo.UserSubDto
import com.yapp.giljob.domain.quest.dto.response.QuestDetailCommonResponseDto
import com.yapp.giljob.domain.subquest.dto.request.SubQuestRequestDto
import com.yapp.giljob.infra.s3.dto.responsne.S3UploadResponseDto

class DtoFactory {
    companion object {
        fun testQuestRequest() = QuestSaveRequestDto(
            name = "test quest",
            position = Position.BACKEND,
            tagList = mutableListOf(testTagResponse()),
            difficulty = 1,
            thumbnail = "test.png",
            detail = "test quest detail",
            subQuestList = listOf(SubQuestRequestDto("sub quest 1"), SubQuestRequestDto("sub quest 2"))
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

        fun testQuestByParticipantResponse() = QuestByParticipantResponseDto(
            id = 1L,
            name = "test quest",
            position = Position.BACKEND,
            difficulty = 1,
            thumbnail = "test.png",
            user = UserSubDto(
                id = 1L,
                nickname = "testNickname",
                point = 100
            ),
            progress = 33
        )

        fun testTagRequest() = TagRequestDto("tag1")
        
        fun testQuestDetailCommonResponse() = QuestDetailCommonResponseDto(
            name = "test quest",
            difficulty = 1,
            position = Position.BACKEND,
            participantCnt = 1L,
            tagList = mutableListOf(testTagResponse())
        )

        fun testTagResponse() = TagResponseDto("tag1")

        fun testSignUpRequest() = SignUpRequestDto(
            kakaoAccessToken = "test",
            position = Position.BACKEND.name,
            nickname = "nickname")

        fun testSignInRequest() = SignInRequestDto(kakaoAccessToken = "test")

        fun testS3UploadResponse() = S3UploadResponseDto(
            fileUrl = "https://giljob.s3.us-east-2.amazonaws.com/0f792d8f-8fc0-49c6-ba39-b77d39024239test.jpeg"
        )

    }
}