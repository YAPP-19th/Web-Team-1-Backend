package com.yapp.giljob.domain.quest.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.yapp.giljob.domain.quest.application.QuestService
import com.yapp.giljob.domain.user.dao.UserRepository
import com.yapp.giljob.global.AbstractRestDocs
import com.yapp.giljob.global.common.dto.DtoFactory
import com.yapp.giljob.global.config.security.GiljobTestUser
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.requestParameters
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(QuestController::class)
internal class QuestControllerTest : AbstractRestDocs() {

    @MockBean
    private lateinit var questService: QuestService

    @MockBean
    private lateinit var userRepository: UserRepository

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
            .andExpect(status().isCreated)
            .andDo(
                MockMvcRestDocumentation.document(
                    "quests/post",
                    HeaderDocumentation.responseHeaders(),
                    HeaderDocumentation.responseHeaders(),
                    PayloadDocumentation.requestFields(
                        PayloadDocumentation.fieldWithPath("name")
                            .description("퀘스트 이름"),
                        PayloadDocumentation.fieldWithPath("difficulty")
                            .description("퀘스트 난이도"),
                        PayloadDocumentation.fieldWithPath("position")
                            .description("카테고리(직군)"),
                        PayloadDocumentation.fieldWithPath("tagList[*].name")
                            .description("태그 이름"),
                        PayloadDocumentation.fieldWithPath("detail")
                            .description("상세 내용"),
                        PayloadDocumentation.fieldWithPath("thumbnail")
                            .description("썸네일 url"),
                        PayloadDocumentation.fieldWithPath("subQuestList[*].name")
                            .description("서브 퀘스트 이름")
                    ),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("201"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("성공 메세지"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("null")
                    )
                )
            )
    }

    @Test
    fun getQuestListTest() {
        given(questService.getQuestList(10, PageRequest.of(0, 4))).willReturn(
            listOf(
                DtoFactory.testQuestResponse().apply { this.id = 9L; this.name = "quest test 9" },
                DtoFactory.testQuestResponse().apply { this.id = 8L; this.name = "quest test 8" },
                DtoFactory.testQuestResponse().apply { this.id = 7L; this.name = "quest test 7" },
                DtoFactory.testQuestResponse().apply { this.id = 6L; this.name = "quest test 6" },
            )
        )

        val result = mockMvc.perform(
            get("/api/quests")
                .param("cursor", "10")
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
                        parameterWithName("cursor").description("마지막으로 조회된 퀘스트 id, 해당 퀘스트보다 오래된 퀘스트 리스트가 조회됩니다."),
                        parameterWithName("size").description("조회할 퀘스트 개수")
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
                        PayloadDocumentation.fieldWithPath("data[*].user.id")
                            .description("퀘스트 작성자 id"),
                        PayloadDocumentation.fieldWithPath("data[*].user.nickname")
                            .description("퀘스트 작성자 nickname"),
                        PayloadDocumentation.fieldWithPath("data[*].difficulty")
                            .description("퀘스트 난이도"),
                        PayloadDocumentation.fieldWithPath("data[*].thumbnail")
                            .description("퀘스트 썸네일 url"),
                    )
                )
            )
    }
}