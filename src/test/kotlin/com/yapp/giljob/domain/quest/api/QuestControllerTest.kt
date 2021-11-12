package com.yapp.giljob.domain.quest.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.yapp.giljob.domain.quest.application.QuestService
import com.yapp.giljob.domain.tag.mapper.TagMapper
import com.yapp.giljob.domain.user.dao.UserRepository
import com.yapp.giljob.global.AbstractRestDocs
import com.yapp.giljob.global.common.domain.EntityFactory
import com.yapp.giljob.global.common.dto.DtoFactory
import com.yapp.giljob.global.config.security.GiljobTestUser
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(QuestController::class)
internal class QuestControllerTest : AbstractRestDocs() {

    @MockBean
    private lateinit var questService: QuestService

    @MockBean
    private lateinit var tagMapper: TagMapper

    @MockBean
    private lateinit var userRepository: UserRepository

    private val user = EntityFactory.testUser()
    private val tag = EntityFactory.testTag()
    private val quest = EntityFactory.testQuest()

    private val questRequest = DtoFactory.testQuestRequest()
    private val tagRequest = DtoFactory.testTagRequest()

    @BeforeEach
    fun setUp() {
        given(tagMapper.toEntity(tagRequest)).willReturn(tag)
    }

    @GiljobTestUser
    @Test
    fun saveQuest() {
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
}