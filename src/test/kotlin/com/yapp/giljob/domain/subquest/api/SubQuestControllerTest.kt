package com.yapp.giljob.domain.subquest.api

import com.yapp.giljob.domain.subquest.application.SubQuestParticipationService
import com.yapp.giljob.domain.subquest.dao.SubQuestParticipationRepository
import com.yapp.giljob.domain.subquest.dao.SubQuestRepository
import com.yapp.giljob.domain.user.dao.UserRepository
import com.yapp.giljob.global.AbstractRestDocs
import com.yapp.giljob.global.config.security.GiljobTestUser
import org.junit.jupiter.api.Assertions.*
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

@WebMvcTest(SubQuestController::class)
class SubQuestControllerTest : AbstractRestDocs() {

    @MockBean
    private lateinit var subQuestParticipationService: SubQuestParticipationService

    @MockBean
    private lateinit var subQuestRepository: SubQuestRepository

    @MockBean
    private lateinit var subQuestParticipationRepository: SubQuestParticipationRepository

    @MockBean
    private lateinit var userRepository: UserRepository
    
    @GiljobTestUser
    @Test
    fun completeSubQuest() {
        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.post("/api/subquests/{subQuestId}", 1L)
                .header("Authorization", "Access Token")
        ).andDo(MockMvcResultHandlers.print())

        result
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "subquests/post",
                    RequestDocumentation.pathParameters(
                        RequestDocumentation.parameterWithName("subQuestId").description("완료한 서브퀘스트 id")
                    ),
                    HeaderDocumentation.responseHeaders(),
                    HeaderDocumentation.responseHeaders(),
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

}