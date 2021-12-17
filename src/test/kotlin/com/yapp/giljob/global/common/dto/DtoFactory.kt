package com.yapp.giljob.global.common.dto

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.dto.request.QuestReviewCreateRequestDto
import com.yapp.giljob.domain.quest.dto.response.QuestResponseDto
import com.yapp.giljob.domain.quest.dto.request.QuestSaveRequestDto
import com.yapp.giljob.domain.quest.dto.response.QuestByParticipantResponseDto
import com.yapp.giljob.domain.sign.dto.request.SignInRequestDto
import com.yapp.giljob.domain.sign.dto.request.SignUpRequestDto
import com.yapp.giljob.domain.tag.dto.response.TagResponseDto
import com.yapp.giljob.domain.subquest.dto.request.SubQuestRequestDto
import com.yapp.giljob.domain.tag.dto.request.TagRequestDto
import com.yapp.giljob.domain.user.dto.request.UserInfoUpdateRequestDto
import com.yapp.giljob.domain.user.dto.request.UserIntroUpdateRequestDto
import com.yapp.giljob.domain.quest.dto.response.QuestDetailInfoResponseDto
import com.yapp.giljob.domain.user.dto.response.*

import com.yapp.giljob.infra.s3.dto.responsne.S3UploadResponseDto

class DtoFactory {
    companion object {
        fun testQuestRequest() = QuestSaveRequestDto(
            name = "test quest",
            position = Position.BACKEND,
            tagList = mutableListOf(testTagRequest()),
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
            participantCount = 100,
            user = UserSubResponseDto(
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
            participantCount = 100,
            user = UserSubResponseDto(
                id = 1L,
                nickname = "testNickname",
                point = 100
            ),
            progress = 33
        )

        fun testTagRequest() = TagRequestDto("tag1")

        fun testTagResponse() = TagResponseDto("tag1")

        fun testQuestDetailInfoResponse() = QuestDetailInfoResponseDto(
            id = 1L,
            name = "test quest",
            difficulty = 1,
            position = Position.BACKEND,
            participantCnt = 1L,
            detail = "test detail",
            writer = UserSubResponseDto(
                id = 1L,
                nickname = "testNickname",
                point = 100
            ),
            tagList = mutableListOf(testTagResponse())
        )

        fun testSignUpRequest() = SignUpRequestDto(
            kakaoAccessToken = "test",
            position = Position.BACKEND.name,
            intro = "testIntro",
            nickname = "nickname"
        )

        fun testSignInRequest() = SignInRequestDto(kakaoAccessToken = "test")

        fun testS3UploadResponse() = S3UploadResponseDto(
            fileUrl = "https://giljob.s3.us-east-2.amazonaws.com/0f792d8f-8fc0-49c6-ba39-b77d39024239test.jpeg"
        )

        fun testUserInfoResponse() = UserInfoResponseDto(
            userId = 1L,
            nickname = "nickname",
            position = Position.BACKEND,
            point = 1000L,
            intro = "test introduce"
        )

        fun testUserProfileResponse() = UserProfileResponseDto(
            userInfo = testUserInfoResponse(),
            abilityList = mutableListOf(
                AbilityResponseDto(Position.BACKEND, 1000L),
                AbilityResponseDto(Position.FRONTEND, 400L)
            ),
            achieve = AchieveResponseDto(5, 4)
        )
        
        fun testUserInfoRequest() = UserInfoUpdateRequestDto(
            nickname = "testNickname",
            position = Position.BACKEND
        )

        fun testUserIntroRequest() = UserIntroUpdateRequestDto(
            intro = "test introduce"
        )

        fun testQuestReviewCreateRequest() = QuestReviewCreateRequestDto(
            review = "퀘스트 한줄 후기"
        )
    }
}