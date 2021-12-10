package com.yapp.giljob.domain.quest.api

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.application.QuestSearchService
import com.yapp.giljob.domain.user.dao.UserRepository
import com.yapp.giljob.global.AbstractRestDocs
import com.yapp.giljob.global.common.dto.DtoFactory
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(QuestSearchController::class)
class QuestSearchControllerTest : AbstractRestDocs() {

    @MockBean
    private lateinit var questSearchService: QuestSearchService

    @MockBean
    private lateinit var userRepository: UserRepository


    @Test
    fun getQuestListTest() {
        BDDMockito.given(questSearchService.search("test", Position.BACKEND,4, 10L)).willReturn(
            listOf(
                DtoFactory.testQuestResponse().apply { this.id = 9L; this.name = "quest Test 9" },
                DtoFactory.testQuestResponse().apply { this.id = 8L; this.name = "quest TEST 8" },
                DtoFactory.testQuestResponse().apply { this.id = 7L; this.name = "quest test 7" },
                DtoFactory.testQuestResponse().apply { this.id = 6L; this.name = "quest test 6" },
            )
        )

        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/quests/search")
                .param("keyword", "test")
                .param("position", "BACKEND")
                .param("size", "4")
                .param("cursor", "10")
        ).andDo(MockMvcResultHandlers.print())

        result
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "quests/search/get",
                    HeaderDocumentation.responseHeaders(),
                    HeaderDocumentation.responseHeaders(),
                    RequestDocumentation.requestParameters(
                        RequestDocumentation.parameterWithName("keyword")
                            .description("검색할 키워드, 대소문자 구분없이 퀘스트 이름을 검색합니다."),
                        RequestDocumentation.parameterWithName("position").description("검색할 포지션(BACKEND, FRONTEND) 전체 검색인 경우 생략 가능합니다."),
                                RequestDocumentation.parameterWithName("size").description("조회할 퀘스트 개수"),
                        RequestDocumentation.parameterWithName("cursor")
                            .description("마지막으로 조회된 퀘스트 id, 해당 퀘스트보다 오래된 퀘스트 리스트가 조회됩니다.")
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

}