package com.yapp.giljob.domain.quest.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.yapp.giljob.domain.quest.application.QuestParticipationService
import com.yapp.giljob.domain.quest.dao.QuestParticipationRepository
import com.yapp.giljob.domain.quest.dto.request.QuestReviewCreateRequestDto
import com.yapp.giljob.domain.quest.dto.response.QuestCountResponseDto
import com.yapp.giljob.domain.user.dao.UserRepository
import com.yapp.giljob.global.AbstractRestDocs
import com.yapp.giljob.global.common.domain.EntityFactory
import com.yapp.giljob.global.common.dto.DtoFactory
import com.yapp.giljob.global.config.security.GiljobTestUser
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.BDDMockito
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(QuestParticipationController::class)
internal class QuestParticipationControllerTest : AbstractRestDocs() {

    @MockBean
    private lateinit var questParticipationService: QuestParticipationService

    @MockBean
    private lateinit var questParticipationRepository: QuestParticipationRepository

    @MockBean
    private lateinit var userRepository: UserRepository

    @GiljobTestUser
    @Test
    fun participateQuest() {
        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.post("/api/quests/{questId}/participation", 1L)
                .header("Authorization", "Access Token")
        ).andDo(MockMvcResultHandlers.print())

        result
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "quests/{questId}/participation/post",
                    RequestDocumentation.pathParameters(
                        RequestDocumentation.parameterWithName("questId").description("참여할 퀘스트 id")
                    ),
                    HeaderDocumentation.responseHeaders(),
                    HeaderDocumentation.responseHeaders(),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("성공 메세지"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("null")
                    )
                )
            )
    }

    @Test
    fun getAllQuestCountTest() {
        BDDMockito.given(questParticipationService.getAllQuestCount()).willReturn(QuestCountResponseDto(124L, 27L, 52L))

        val result = mockMvc.perform(RestDocumentationRequestBuilders.get("/api/quests/count")).andDo(
            MockMvcResultHandlers.print()
        )

        result
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "quests/count/get",
                    HeaderDocumentation.responseHeaders(),
                    HeaderDocumentation.responseHeaders(),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("성공 메세지"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("응답 데이터"),
                        PayloadDocumentation.fieldWithPath("data.totalQuestCount")
                            .description("전체 퀘스트 수"),
                        PayloadDocumentation.fieldWithPath("data.onProgressQuestCount")
                            .description("진행 중 퀘스트 수"),
                        PayloadDocumentation.fieldWithPath("data.totalParticipantCount")
                            .description("퀘스트 참여자 수"),
                    )
                )
            )
    }

    @GiljobTestUser
    @Test
    fun completeQuestTest() {
        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.patch("/api/quests/{questId}/complete", 1L)
                .header("Authorization", "Access Token")
        ).andDo(
            MockMvcResultHandlers.print()
        )

        result
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "quests/{questId}/complete/patch",
                    HeaderDocumentation.responseHeaders(),
                    HeaderDocumentation.responseHeaders(),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("성공 메세지"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("null")
                    )
                )
            )
    }

    @GiljobTestUser
    @Test
    fun createQuestReviewTest() {
        BDDMockito.given(questParticipationRepository.getQuestParticipationByQuestIdAndParticipantId(anyLong(), anyLong()))
            .willReturn(EntityFactory.testQuestParticipation())

        val jsonString = ObjectMapper().writeValueAsString(DtoFactory.testQuestReviewCreateRequest())

        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.patch("/api/quests/{questId}/review", 1L)
                .header("Authorization", "Access Token")
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())

        result
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "quests/review/patch",
                    HeaderDocumentation.responseHeaders(),
                    HeaderDocumentation.responseHeaders(),
                    PayloadDocumentation.requestFields(
                        PayloadDocumentation.fieldWithPath("review")
                            .description("한줄 후기")
                    ),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("성공 메세지"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("null")
                    )
                )
            )
    }

    @Test
    @GiljobTestUser
    fun getQuestParticipationStatusTest() {
        BDDMockito.given(questParticipationService.getQuestParticipationStatus(anyLong(), anyLong())).willReturn("아직 참여하지 않은 퀘스트입니다.")

        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/quests/{questId}/participation/status?userId={userId}", 1, 1)
        ).andDo(MockMvcResultHandlers.print())

        result
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                "quests/participation/status/get",
                RequestDocumentation.pathParameters(
                    RequestDocumentation.parameterWithName("questId").description("퀘스트 id")
                ),
                    RequestDocumentation.requestParameters(
                        RequestDocumentation.parameterWithName("userId")
                            .description("조회할 user id")
                    ),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("성공 메세지"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("퀘스트와 유저와의 관계 : 참여 전 / 참여중 / 참여 완료"),
                    )
                )
            )
    }
}