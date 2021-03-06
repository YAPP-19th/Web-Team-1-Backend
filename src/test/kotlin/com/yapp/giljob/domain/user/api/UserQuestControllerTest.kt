package com.yapp.giljob.domain.user.api

import com.yapp.giljob.domain.user.application.UserQuestService
import com.yapp.giljob.domain.user.dao.UserRepository
import com.yapp.giljob.global.AbstractRestDocs
import com.yapp.giljob.global.common.dto.DtoFactory
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageRequest
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(UserQuestController::class)
class UserQuestControllerTest : AbstractRestDocs() {

    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    private fun <T> uninitialized(): T = null as T

    @MockBean
    private lateinit var userQuestService: UserQuestService

    @MockBean
    private lateinit var userRepository: UserRepository

    private val userId = 1L

    @Test
    fun getcontentListByUserTest() {
        BDDMockito.given(userQuestService.getQuestListByUser(any(), any())).willReturn(DtoFactory.testQuestResponse())

        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/users/{userId}/quests", userId)
                .param("page", "0")
                .param("size", "4")
        ).andDo(MockMvcResultHandlers.print())

        result
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "users/{userId}/quests/get",
                    HeaderDocumentation.responseHeaders(),
                    HeaderDocumentation.responseHeaders(),
                    RequestDocumentation.requestParameters(
                        RequestDocumentation.parameterWithName("page").description("????????? ??????"),
                        RequestDocumentation.parameterWithName("size").description("????????? ????????? ??????")
                    ),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("?????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("????????? ?????????"),
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
    fun getContentListByParticipantTest() {
        BDDMockito.given(userQuestService.getQuestListByParticipant(userId, false, PageRequest.of(0, 4)))
            .willReturn(DtoFactory.testQuestByParticipantResponse())

        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/users/{userId}/quests/participation", userId)
                .param("completed", "false")
                .param("page", "0")
                .param("size", "4")
        ).andDo(MockMvcResultHandlers.print())

        result
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "users/{userId}/quests/participation/get",
                    HeaderDocumentation.responseHeaders(),
                    HeaderDocumentation.responseHeaders(),
                    RequestDocumentation.requestParameters(
                        RequestDocumentation.parameterWithName("page").description("????????? ??????"),
                        RequestDocumentation.parameterWithName("size").description("????????? ????????? ??????"),
                        RequestDocumentation.parameterWithName("completed").description("?????? ????????? ??????(?????????: false)")
                    ),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("?????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("????????? ?????????"),
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
                        PayloadDocumentation.fieldWithPath("data.contentList[*].progress")
                            .description("????????? ?????????(????????? ??????/Int)")
                    )
                )
            )
    }
}