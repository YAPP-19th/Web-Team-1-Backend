package com.yapp.giljob.global.common.dto

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.dto.QuestDto
import com.yapp.giljob.domain.quest.dto.request.QuestRequestDto
import com.yapp.giljob.domain.quest.dto.request.QuestReviewCreateRequestDto
import com.yapp.giljob.domain.quest.dto.request.QuestSaveRequestDto
import com.yapp.giljob.domain.quest.dto.response.*
import com.yapp.giljob.domain.roadmap.dto.request.RoadmapSaveRequestDto
import com.yapp.giljob.domain.roadmap.dto.response.RoadmapDetailResponseDto
import com.yapp.giljob.domain.roadmap.dto.response.RoadmapResponseDto
import com.yapp.giljob.domain.sign.dto.request.SignInRequestDto
import com.yapp.giljob.domain.sign.dto.request.SignUpRequestDto
import com.yapp.giljob.domain.subquest.dto.request.SubQuestRequestDto
import com.yapp.giljob.domain.subquest.dto.response.SubQuestProgressResponseDto
import com.yapp.giljob.domain.tag.dto.request.TagRequestDto
import com.yapp.giljob.domain.tag.dto.response.TagResponseDto
import com.yapp.giljob.domain.user.dto.request.UserInfoUpdateRequestDto
import com.yapp.giljob.domain.user.dto.request.UserIntroUpdateRequestDto
import com.yapp.giljob.domain.user.dto.response.AbilityResponseDto
import com.yapp.giljob.domain.user.dto.response.AchieveResponseDto
import com.yapp.giljob.domain.user.dto.response.UserInfoResponseDto
import com.yapp.giljob.domain.user.dto.response.UserProfileResponseDto
import com.yapp.giljob.infra.s3.dto.responsne.S3UploadResponseDto
import java.time.LocalDateTime

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

        fun testQuestDetailResponse() = QuestDetailResponseDto(
            id = 1L,
            name = "test quest",
            position = Position.BACKEND,
            difficulty = 1,
            thumbnail = "test.png",
            participantCount = 100,
            writer = UserInfoResponseDto(
                id = 1L,
                nickname = "testNickname",
                position = Position.BACKEND,
                point = 100,
                intro = "자기소개 테스트"
            )
        )

        fun testQuestResponse(): ListResponseDto<QuestDetailResponseDto> {
            val questList = listOf(
                testQuestDetailResponse().apply { this.id = 9L; this.name = "quest test 9" },
                testQuestDetailResponse().apply { this.id = 8L; this.name = "quest test 8" },
                testQuestDetailResponse().apply { this.id = 7L; this.name = "quest test 7" },
                testQuestDetailResponse().apply { this.id = 6L; this.name = "quest test 6" },
            )
            return ListResponseDto(
                questList.size.toLong(), questList
            )
        }


        fun testQuestByParticipantResponse(): ListResponseDto<QuestByParticipantResponseDto> {
            val questList = listOf(
                testQuestDetailByParticipantResponse()
                    .apply { this.id = 9L; this.name = "quest test 9"; this.progress = 90 },
                testQuestDetailByParticipantResponse()
                    .apply { this.id = 7L; this.name = "quest test 7"; this.progress = 70 },
                testQuestDetailByParticipantResponse()
                    .apply { this.id = 6L; this.name = "quest test 6"; this.progress = 33 },
                testQuestDetailByParticipantResponse()
                    .apply { this.id = 3L; this.name = "quest test 3"; this.progress = 50 },
            )
            return ListResponseDto(
                questList.size.toLong(), questList
            )
        }

        fun testQuestDetailByParticipantResponse() = QuestByParticipantResponseDto(
            id = 1L,
            name = "test quest",
            position = Position.BACKEND,
            difficulty = 1,
            thumbnail = "test.png",
            participantCount = 100,
            writer = testUserInfoResponse(),
            progress = 33
        )

        fun testTagRequest() = TagRequestDto("tag1")

        fun testTagResponse() = TagResponseDto("tag1")

        fun testQuestDetailInfoResponse() = QuestDetailInfoResponseDto(
            id = 1L,
            name = "test quest",
            difficulty = 1,
            position = Position.BACKEND,
            participantCount = 1L,
            detail = "test detail",
            writer = testUserInfoResponse(),
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
            id = 1L,
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

        fun testDetailRoadmapResponse() = RoadmapDetailResponseDto(
            name = "로드맵 테스트",
            writer = testUserInfoResponse(),
            position = Position.FRONTEND,
            questList = listOf(
                QuestDto(1L, "코딩 스터디", true),
                QuestDto(2L, "OOO 자격증", false),
                QuestDto(3L, "연합 동아리", false),
                QuestDto(4L, "javascript 스터디", true),
            ),
            isScraped = false
        )

        fun testRoadmapResponse() = RoadmapResponseDto(
            id = 1L,
            name = "로드맵 테스트",
            writer = testUserInfoResponse(),
            position = Position.FRONTEND
        )

        fun testRoadmapSaveRequest() = RoadmapSaveRequestDto(
            name = "로드맵",
            position = Position.BACKEND,
            questList = mutableListOf(
                QuestRequestDto(
                    questId = 1L,
                    name = "실제 퀘스트 인 경우 아이디만 입력합니다."
                ),
                QuestRequestDto(
                    questId = 0L,
                    name = "실제 퀘스트가 아닌 경우 이름만 입력합니다."
                )
            )
        )

        fun testQuestDetailSubQuestResponseDto() = QuestDetailSubQuestResponseDto(
            progress = 50,
            subQuestProgressList = mutableListOf(
                SubQuestProgressResponseDto(
                    subQuestId = 1,
                    subQuestName = "서브퀘스트1",
                    isCompleted = true
                ),
                SubQuestProgressResponseDto(
                    subQuestId = 2,
                    subQuestName = "서브퀘스트2",
                    isCompleted = false
                )
            )
        )

        fun testQuestPositionCountResponse() = QuestPositionCountResponseDto(
            position = Position.BACKEND.name,
            questCount = 10
        )
    }
}