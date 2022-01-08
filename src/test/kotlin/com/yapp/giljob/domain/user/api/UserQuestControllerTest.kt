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
    fun getQuestListByUserTest() {
        BDDMockito.given(userQuestService.getQuestListByUser(any(), any())).willReturn(
            listOf(
                DtoFactory.testQuestResponse().apply { this.id = 9L; this.name = "quest test 9" },
                DtoFactory.testQuestResponse().apply { this.id = 7L; this.name = "quest test 7" },
                DtoFactory.testQuestResponse().apply { this.id = 6L; this.name = "quest test 6" },
                DtoFactory.testQuestResponse().apply { this.id = 3L; this.name = "quest test 3" },
            )
        )

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
                        PayloadDocumentation.fieldWithPath("data[*].id")
                            .description("퀘스트 id"),
                        PayloadDocumentation.fieldWithPath("data[*].name")
                            .description("퀘스트 이름"),
                        PayloadDocumentation.fieldWithPath("data[*].position")
                            .description("퀘스트 카테고리(position)"),
                        PayloadDocumentation.fieldWithPath("data[*].participantCount")
                            .description("퀘스트 참여자 수"),
                        PayloadDocumentation.fieldWithPath("data[*].writer.id")
                            .description("퀘스트 작성자 id"),
                        PayloadDocumentation.fieldWithPath("data[*].writer.nickname")
                            .description("퀘스트 작성자 nickname"),
                        PayloadDocumentation.fieldWithPath("data[*].writer.position")
                            .description("퀘스트 작성자 직군"),
                        PayloadDocumentation.fieldWithPath("data[*].writer.point")
                            .description("퀘스트 작성자 능력치"),
                        PayloadDocumentation.fieldWithPath("data[*].writer.intro")
                            .description("퀘스트 작성자 자기소개"),
                        PayloadDocumentation.fieldWithPath("data[*].difficulty")
                            .description("퀘스트 난이도"),
                        PayloadDocumentation.fieldWithPath("data[*].thumbnail")
                            .description("퀘스트 썸네일 url"),
                    )
                )
            )
    }

    @Test
    fun getQuestListByParticipantTest() {
        BDDMockito.given(userQuestService.getQuestListByParticipant(userId, false, PageRequest.of(0, 4))).willReturn(
            listOf(
                DtoFactory.testQuestByParticipantResponse()
                    .apply { this.id = 9L; this.name = "quest test 9"; this.progress = 90 },
                DtoFactory.testQuestByParticipantResponse()
                    .apply { this.id = 7L; this.name = "quest test 7"; this.progress = 70 },
                DtoFactory.testQuestByParticipantResponse()
                    .apply { this.id = 6L; this.name = "quest test 6"; this.progress = 33 },
                DtoFactory.testQuestByParticipantResponse()
                    .apply { this.id = 3L; this.name = "quest test 3"; this.progress = 50 },
            )
        )

        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/users/{userId}/quests/participation", userId)
                .param("cursor", "10")
                .param("completed", "false")
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
                        RequestDocumentation.parameterWithName("cursor")
                            .description("마지막으로 조회된 퀘스트 id, 해당 퀘스트보다 오래된 퀘스트 리스트가 조회됩니다."),
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
                        PayloadDocumentation.fieldWithPath("data[*].id")
                            .description("퀘스트 id"),
                        PayloadDocumentation.fieldWithPath("data[*].name")
                            .description("퀘스트 이름"),
                        PayloadDocumentation.fieldWithPath("data[*].position")
                            .description("퀘스트 카테고리(position)"),
                        PayloadDocumentation.fieldWithPath("data[*].participantCount")
                            .description("퀘스트 참여자 수"),
                        PayloadDocumentation.fieldWithPath("data[*].writer.id")
                            .description("퀘스트 작성자 id"),
                        PayloadDocumentation.fieldWithPath("data[*].writer.nickname")
                            .description("퀘스트 작성자 nickname"),
                        PayloadDocumentation.fieldWithPath("data[*].writer.position")
                            .description("퀘스트 작성자 직군"),
                        PayloadDocumentation.fieldWithPath("data[*].writer.point")
                            .description("퀘스트 작성자 능력치"),
                        PayloadDocumentation.fieldWithPath("data[*].writer.intro")
                            .description("퀘스트 작성자 자기소개"),
                        PayloadDocumentation.fieldWithPath("data[*].difficulty")
                            .description("퀘스트 난이도"),
                        PayloadDocumentation.fieldWithPath("data[*].progress")
                            .description("퀘스트 진행률(퍼센트 단위/Int)"),
                        PayloadDocumentation.fieldWithPath("data[*].thumbnail")
                            .description("퀘스트 썸네일 url"),
                    )
                )
            )
    }
}