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
                        RequestDocumentation.parameterWithName("page").description("페이지 번호"),
                        RequestDocumentation.parameterWithName("size").description("조회할 퀘스트 개수")
                    ),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("성공 메세지"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("퀘스트 리스트"),
                        PayloadDocumentation.fieldWithPath("data.totalCount")
                            .description("퀘스트 전체 개수"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].id")
                            .description("퀘스트 id"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].name")
                            .description("퀘스트 이름"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].position")
                            .description("퀘스트 카테고리(position)"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].participantCount")
                            .description("퀘스트 참여자 수"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].writer.id")
                            .description("퀘스트 작성자 id"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].writer.nickname")
                            .description("퀘스트 작성자 nickname"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].writer.position")
                            .description("퀘스트 작성자 직군"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].writer.point")
                            .description("퀘스트 작성자 능력치"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].writer.intro")
                            .description("퀘스트 작성자 자기소개"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].difficulty")
                            .description("퀘스트 난이도"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].thumbnail")
                            .description("퀘스트 썸네일 url"),
                    )
                )
            )
    }

    @Test
    fun getcontentListByParticipantTest() {
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
                        RequestDocumentation.parameterWithName("page").description("페이지 번호"),
                        RequestDocumentation.parameterWithName("size").description("조회할 퀘스트 개수"),
                        RequestDocumentation.parameterWithName("completed").description("완료 퀘스트 여부(기본값: false)")
                    ),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("성공 메세지"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("퀘스트 리스트"),
                        PayloadDocumentation.fieldWithPath("data.totalCount")
                            .description("퀘스트 전체 개수"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].id")
                            .description("퀘스트 id"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].name")
                            .description("퀘스트 이름"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].position")
                            .description("퀘스트 카테고리(position)"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].participantCount")
                            .description("퀘스트 참여자 수"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].writer.id")
                            .description("퀘스트 작성자 id"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].writer.nickname")
                            .description("퀘스트 작성자 nickname"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].writer.position")
                            .description("퀘스트 작성자 직군"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].writer.point")
                            .description("퀘스트 작성자 능력치"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].writer.intro")
                            .description("퀘스트 작성자 자기소개"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].difficulty")
                            .description("퀘스트 난이도"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].thumbnail")
                            .description("퀘스트 썸네일 url"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].progress")
                            .description("퀘스트 진행률(퍼센트 단위/Int)")
                    )
                )
            )
    }
}