package com.yapp.giljob.domain.quest.api

import com.yapp.giljob.domain.quest.application.QuestParticipationService
import com.yapp.giljob.domain.quest.dao.QuestParticipationRepository
import com.yapp.giljob.domain.user.dao.UserRepository
import com.yapp.giljob.global.AbstractRestDocs
import com.yapp.giljob.global.config.security.GiljobTestUser
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

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
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andDo(
                MockMvcRestDocumentation.document(
                    "quests/participation/post",
                    RequestDocumentation.pathParameters(
                        RequestDocumentation.parameterWithName("questId").description("참여할 퀘스트 id")
                    ),
                    HeaderDocumentation.responseHeaders(),
                    HeaderDocumentation.responseHeaders(),
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