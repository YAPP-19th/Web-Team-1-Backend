package com.yapp.giljob.domain.quest.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.yapp.giljob.domain.quest.application.QuestParticipationService
import com.yapp.giljob.domain.quest.dao.QuestParticipationRepository
import com.yapp.giljob.domain.quest.dto.response.QuestCountResponseDto
import com.yapp.giljob.domain.quest.dto.response.QuestReviewResponseDto
import com.yapp.giljob.domain.user.dao.UserRepository
import com.yapp.giljob.global.AbstractRestDocs
import com.yapp.giljob.global.common.domain.EntityFactory
import com.yapp.giljob.global.common.dto.DtoFactory
import com.yapp.giljob.global.common.dto.ListResponseDto
import com.yapp.giljob.global.config.security.GiljobTestUser
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.BDDMockito
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDateTime

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
                        RequestDocumentation.parameterWithName("questId").description("????????? ????????? id")
                    ),
                    HeaderDocumentation.responseHeaders(),
                    HeaderDocumentation.responseHeaders(),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("?????? ?????????"),
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
                            .description("?????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("?????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data.totalQuestCount")
                            .description("?????? ????????? ???"),
                        PayloadDocumentation.fieldWithPath("data.onProgressQuestCount")
                            .description("?????? ??? ????????? ???"),
                        PayloadDocumentation.fieldWithPath("data.totalParticipantCount")
                            .description("????????? ????????? ???"),
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
                    RequestDocumentation.pathParameters(
                        RequestDocumentation.parameterWithName("questId").description("????????? ????????? id")
                    ),
                    HeaderDocumentation.responseHeaders(),
                    HeaderDocumentation.responseHeaders(),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("?????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("null")
                    )
                )
            )
    }

    @GiljobTestUser
    @Test
    fun cancelQuestTest() {
        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.patch("/api/quests/{questId}/cancel", 1L)
                .header("Authorization", "Access Token")
        ).andDo(
            MockMvcResultHandlers.print()
        )

        result
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "quests/{questId}/cancel/patch",
                    RequestDocumentation.pathParameters(
                        RequestDocumentation.parameterWithName("questId").description("????????? ????????? id")
                    ),
                    HeaderDocumentation.responseHeaders(),
                    HeaderDocumentation.responseHeaders(),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("?????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("null")
                    )
                )
            )
    }

    @GiljobTestUser
    @Test
    fun createQuestReviewTest() {
        BDDMockito.given(questParticipationRepository.findByQuestIdAndParticipantId(anyLong(), anyLong()))
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
                            .description("?????? ??????")
                    ),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("?????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("null")
                    )
                )
            )
    }

    @Test
    @GiljobTestUser
    fun getQuestParticipationStatusTest() {
        BDDMockito.given(questParticipationService.getQuestParticipationStatus(anyLong(), anyLong())).willReturn("?????? ???????????? ?????? ??????????????????.")

        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/quests/{questId}/participation/status?userId={userId}", 1, 1)
        ).andDo(MockMvcResultHandlers.print())

        result
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                "quests/participation/status/get",
                RequestDocumentation.pathParameters(
                    RequestDocumentation.parameterWithName("questId").description("????????? id")
                ),
                    RequestDocumentation.requestParameters(
                        RequestDocumentation.parameterWithName("userId")
                            .description("????????? user id")
                    ),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("?????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("???????????? ???????????? ?????? : ?????? ??? / ????????? / ?????? ??????"),
                    )
                )
            )
    }

    @Test
    fun getQuestReviewTest() {
        BDDMockito.given(questParticipationService.getQuestReviewList(1, Pageable.ofSize(5)))
            .willReturn(ListResponseDto(
                totalCount = 5,
                contentList = listOf(
                    QuestReviewResponseDto("?????? 1", LocalDateTime.now(), DtoFactory.testUserInfoResponse()),
                    QuestReviewResponseDto("?????? 2", LocalDateTime.now(), DtoFactory.testUserInfoResponse()),
                    QuestReviewResponseDto("?????? 3", LocalDateTime.now(), DtoFactory.testUserInfoResponse()),
                    QuestReviewResponseDto("?????? 4", LocalDateTime.now(), DtoFactory.testUserInfoResponse()),
                    QuestReviewResponseDto("?????? 5", LocalDateTime.now(), DtoFactory.testUserInfoResponse()),
                )
            ))

        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/quests/{questId}/reviews", 1)
                .param("page", "0")
                .param("size", "5")
        ).andDo(MockMvcResultHandlers.print())

        result
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "quests/{questId}/reviews",
                    RequestDocumentation.pathParameters(
                        RequestDocumentation.parameterWithName("questId").description("????????? id")
                    ),
                    RequestDocumentation.requestParameters(
                        RequestDocumentation.parameterWithName("page")
                            .description("????????? ?????????"),
                        RequestDocumentation.parameterWithName("size").description("????????? ??? ??? ?????? ??????")
                    ),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("?????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data.totalCount")
                            .description("????????? ?????? ??? ??????"),
                        PayloadDocumentation.fieldWithPath("data.contentList")
                            .description("????????? ?????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].review")
                            .description("????????? ??????"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].reviewCreatedAt")
                            .description("????????? ?????? ?????? ??????"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].reviewWriter")
                            .description("????????? ?????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].reviewWriter.id")
                            .description("????????? ?????? ????????? id"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].reviewWriter.nickname")
                            .description("????????? ?????? nickname"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].reviewWriter.position")
                            .description("????????? ?????? ????????? ??????"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].reviewWriter.point")
                            .description("????????? ?????? ????????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].reviewWriter.intro")
                            .description("????????? ?????? ????????? ??????"),
                    )
                )
            )
    }
}