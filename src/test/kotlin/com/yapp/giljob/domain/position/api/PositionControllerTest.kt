package com.yapp.giljob.domain.position.api

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.application.QuestService
import com.yapp.giljob.domain.user.dao.UserRepository
import com.yapp.giljob.global.AbstractRestDocs
import com.yapp.giljob.global.common.dto.DtoFactory
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(PositionController::class)
internal class PositionControllerTest : AbstractRestDocs() {

    @MockBean
    private lateinit var questService: QuestService

    @MockBean
    private lateinit var userRepository: UserRepository

    @Test
    fun getQuestPositionCount() {
        BDDMockito.given(questService.getQuestPositionCount())
            .willReturn(
                listOf(
                    DtoFactory.testQuestPositionCountResponse(),
                    DtoFactory.testQuestPositionCountResponse().apply { this.position = Position.BACKEND.name },
                    DtoFactory.testQuestPositionCountResponse().apply { this.position = Position.FRONTEND.name }
                )
            )

        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/quests/positions/count")
        ).andDo(MockMvcResultHandlers.print())

        result
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "quests/positions/count/get",
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("성공 메세지"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("응답 데이터"),
                        PayloadDocumentation.fieldWithPath("data[*].position")
                            .description("퀘스트 포지션"),
                        PayloadDocumentation.fieldWithPath("data[*].questCount")
                            .description("퀘스트 포지션별 개수"),
                    )
                )
            )
    }
}