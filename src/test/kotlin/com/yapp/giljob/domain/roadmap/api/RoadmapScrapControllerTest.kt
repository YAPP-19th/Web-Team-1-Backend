package com.yapp.giljob.domain.roadmap.api

import com.yapp.giljob.domain.roadmap.application.RoadmapScrapService
import com.yapp.giljob.domain.roadmap.dao.RoadmapRepository
import com.yapp.giljob.domain.roadmap.dao.RoadmapScrapRepository
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

@WebMvcTest(RoadmapScrapController::class)
class RoadmapScrapControllerTest : AbstractRestDocs() {
    @MockBean
    private lateinit var roadmapScrapService: RoadmapScrapService

    @MockBean
    private lateinit var roadmapRepository: RoadmapRepository

    @MockBean
    private lateinit var roadmapScrapRepository: RoadmapScrapRepository

    @MockBean
    private lateinit var userRepository: UserRepository

    @GiljobTestUser
    @Test
    fun scrapRoadmap() {
        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.post("/api/roadmaps/{roadmapId}/scrap", 1L)
                .header("Authorization", "Access Token")
        ).andDo(MockMvcResultHandlers.print())

        result
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "roadmaps/{roadmapId}/scrap/post",
                    RequestDocumentation.pathParameters(
                        RequestDocumentation.parameterWithName("roadmapId").description("스크랩할 로드맵 id")
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