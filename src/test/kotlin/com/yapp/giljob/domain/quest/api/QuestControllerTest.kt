package com.yapp.giljob.domain.quest.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.application.QuestService
import com.yapp.giljob.domain.user.dao.UserRepository
import com.yapp.giljob.global.AbstractRestDocs
import com.yapp.giljob.global.common.dto.DtoFactory
import com.yapp.giljob.global.config.security.GiljobTestUser
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.*
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
            .andExpect(status().isOk)
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
    fun getQuestListTest() {
        given(questService.getQuestList(10, Position.ALL, 4L)).willReturn(
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
                            .description("응답 데이터(퀘스트 리스트)"),
                        PayloadDocumentation.fieldWithPath("data[*].id")
                            .description("퀘스트 id"),
                        PayloadDocumentation.fieldWithPath("data[*].name")
                            .description("퀘스트 이름"),
                        PayloadDocumentation.fieldWithPath("data[*].position")
                            .description("퀘스트 카테고리(position)"),
                        PayloadDocumentation.fieldWithPath("data[*].participantCount")
                            .description("퀘스트 참여자 수"),
                        PayloadDocumentation.fieldWithPath("data[*].user.id")
                            .description("퀘스트 작성자 id"),
                        PayloadDocumentation.fieldWithPath("data[*].user.nickname")
                            .description("퀘스트 작성자 nickname"),
                        PayloadDocumentation.fieldWithPath("data[*].user.point")
                            .description("퀘스트 작성자 능력치"),
                        PayloadDocumentation.fieldWithPath("data[*].difficulty")
                            .description("퀘스트 난이도"),
                        PayloadDocumentation.fieldWithPath("data[*].thumbnail")
                            .description("퀘스트 썸네일 url"),
                    )
                )
            )
    }

    @Test
    @GiljobTestUser
    @Disabled
    fun `getQuestDetailInfo 성공`() {
        given(questService.getQuestDetailInfo(anyLong(), any())).willReturn(DtoFactory.testQuestDetailInfoResponse())

        val result = mockMvc.perform(
            get("/api/quests/{questId}/info", 1L)
                .header("Authorization", "Access Token")
        ).andDo(print())

        result
            .andExpect(status().isOk)
            .andDo(MockMvcRestDocumentation.document(
                "quests/info/get",
                    pathParameters(
                        parameterWithName("questId").description("퀘스트 id")
                    ),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("성공 메세지"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("응답 데이터"),
                        PayloadDocumentation.fieldWithPath("data.id")
                            .description("퀘스트 id"),
                        PayloadDocumentation.fieldWithPath("data.name")
                            .description("퀘스트 이름"),
                        PayloadDocumentation.fieldWithPath("data.difficulty")
                            .description("퀘스트 난이도"),
                        PayloadDocumentation.fieldWithPath("data.position")
                            .description("퀘스트 카테고리(position)"),
                        PayloadDocumentation.fieldWithPath("data.detail")
                            .description("퀘스트 상세 설명"),
                        PayloadDocumentation.fieldWithPath("data.participantCnt")
                            .description("퀘스트 참여자 수"),
                        PayloadDocumentation.fieldWithPath("data.userStatus")
                            .description("퀘스트와 로그인 유저의 상태"),
                        PayloadDocumentation.fieldWithPath("data.writer.id")
                            .description("퀘스트 작성자 id"),
                        PayloadDocumentation.fieldWithPath("data.writer.nickname")
                            .description("퀘스트 작성자 nickname"),
                        PayloadDocumentation.fieldWithPath("data.writer.point")
                            .description("퀘스트 작성자 point"),
                        PayloadDocumentation.fieldWithPath("data.tagList")
                            .description("퀘스트 tag list"),
                        PayloadDocumentation.fieldWithPath(("data.tagList[*].name"))
                            .description("퀘스트 tag 이름")
                    )
                )
            )
    }
}