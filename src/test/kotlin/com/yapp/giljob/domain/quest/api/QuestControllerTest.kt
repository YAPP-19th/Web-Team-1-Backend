package com.yapp.giljob.domain.quest.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.yapp.giljob.domain.quest.application.QuestService
import com.yapp.giljob.domain.subquest.application.SubQuestParticipationService
import com.yapp.giljob.domain.user.dao.UserRepository
import com.yapp.giljob.global.AbstractRestDocs
import com.yapp.giljob.global.common.dto.DtoFactory
import com.yapp.giljob.global.config.security.GiljobTestUser
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.anyLong
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.request.RequestDocumentation.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(QuestController::class)
class QuestControllerTest : AbstractRestDocs() {

    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    private fun <T> uninitialized(): T = null as T

    @MockBean
    private lateinit var questService: QuestService

    @MockBean
    private lateinit var userRepository: UserRepository

    @MockBean
    private lateinit var subQuestParticipationService: SubQuestParticipationService

    @GiljobTestUser
    @Test
    fun saveQuest() {
        val questRequest = DtoFactory.testQuestRequest()
        val jsonString = ObjectMapper().writeValueAsString(questRequest)
        val result = mockMvc.perform(
            post("/api/quests")
                .header("Authorization", "Access Token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
        ).andDo(print())

        result
            .andExpect(status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "quests/post",
                    HeaderDocumentation.responseHeaders(),
                    HeaderDocumentation.responseHeaders(),
                    PayloadDocumentation.requestFields(
                        PayloadDocumentation.fieldWithPath("name")
                            .description("????????? ??????"),
                        PayloadDocumentation.fieldWithPath("difficulty")
                            .description("????????? ?????????"),
                        PayloadDocumentation.fieldWithPath("position")
                            .description("????????????(??????)"),
                        PayloadDocumentation.fieldWithPath("tagList[*].name")
                            .description("?????? ??????"),
                        PayloadDocumentation.fieldWithPath("detail")
                            .description("?????? ??????"),
                        PayloadDocumentation.fieldWithPath("thumbnail")
                            .description("????????? url"),
                        PayloadDocumentation.fieldWithPath("subQuestList[*].name")
                            .description("?????? ????????? ??????")
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
    fun getContentListTest() {
        given(questService.getQuestList(any(), any())).willReturn(DtoFactory.testQuestResponse())

        val result = mockMvc.perform(
            get("/api/quests")
                .param("page", "0")
                .param("size", "4")
        ).andDo(print())

        result
            .andExpect(status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "quests/get",
                    HeaderDocumentation.responseHeaders(),
                    HeaderDocumentation.responseHeaders(),
                    requestParameters(
                        parameterWithName("page").description("????????? ??????"),
                        parameterWithName("size").description("????????? ????????? ??????")
                    ),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("?????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("?????? ?????????(????????? ?????????)"),
                        PayloadDocumentation.fieldWithPath("data.totalCount")
                            .description("????????? ?????? ??????"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].id")
                            .description("????????? id"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].name")
                            .description("????????? ??????"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].position")
                            .description("????????? ????????????(position)"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].participantCount")
                            .description("????????? ????????? ???"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].writer.id")
                            .description("????????? ????????? id"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].writer.nickname")
                            .description("????????? ????????? nickname"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].writer.position")
                            .description("????????? ????????? ??????"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].writer.point")
                            .description("????????? ????????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].writer.intro")
                            .description("????????? ????????? ????????????"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].difficulty")
                            .description("????????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].thumbnail")
                            .description("????????? ????????? url"),
                    )
                )
            )
    }

    @Test
    fun `getQuestDetailInfo ??????`() {
        given(questService.getQuestDetailInfo(anyLong())).willReturn(DtoFactory.testQuestDetailInfoResponse())

        val result = mockMvc.perform(
            get("/api/quests/{questId}/info", 1L)
        ).andDo(print())

        result
            .andExpect(status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "quests/info/get",
                    pathParameters(
                        parameterWithName("questId").description("????????? id")
                    ),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("?????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("?????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data.id")
                            .description("????????? id"),
                        PayloadDocumentation.fieldWithPath("data.name")
                            .description("????????? ??????"),
                        PayloadDocumentation.fieldWithPath("data.difficulty")
                            .description("????????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data.position")
                            .description("????????? ????????????(position)"),
                        PayloadDocumentation.fieldWithPath("data.detail")
                            .description("????????? ?????? ??????"),
                        PayloadDocumentation.fieldWithPath("data.participantCount")
                            .description("????????? ????????? ???"),
                        PayloadDocumentation.fieldWithPath("data.writer.id")
                            .description("????????? ????????? id"),
                        PayloadDocumentation.fieldWithPath("data.writer.nickname")
                            .description("????????? ????????? nickname"),
                        PayloadDocumentation.fieldWithPath("data.writer.position")
                            .description("????????? ????????? ??????"),
                        PayloadDocumentation.fieldWithPath("data.writer.point")
                            .description("????????? ????????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data.writer.intro")
                            .description("????????? ????????? ????????????"),
                        PayloadDocumentation.fieldWithPath("data.tagList")
                            .description("????????? tag list"),
                        PayloadDocumentation.fieldWithPath(("data.tagList[*].name"))
                            .description("????????? tag ??????")
                    )
                )
            )
    }

    @Test
    @GiljobTestUser
    fun `getQuestDetailSubQuest ??????`() {
        given(subQuestParticipationService.getQuestDetailSubQuestProgress(anyLong(), any()))
            .willReturn(DtoFactory.testQuestDetailSubQuestResponseDto())

        val result = mockMvc.perform(
            get("/api/quests/{questId}/subquests", 1)
                .header("Authorization", "Access Token")
        ).andDo(print())

        result
            .andExpect(status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "quests/{questId}/subquests/get",
                    pathParameters(
                        parameterWithName("questId").description("????????? id")
                    ),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("?????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("?????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data.progress")
                            .description("?????? ????????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data.subQuestProgressList")
                            .description("?????? ????????? ?????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data.subQuestProgressList[*].subQuestId")
                            .description("?????? ????????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data.subQuestProgressList[*].subQuestName")
                            .description("?????? ????????? ??????"),
                        PayloadDocumentation.fieldWithPath("data.subQuestProgressList[*].isCompleted")
                            .description("?????? ????????? ?????? ??????"),
                    )
                )
            )
    }
}