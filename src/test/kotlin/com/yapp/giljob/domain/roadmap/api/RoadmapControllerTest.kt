package com.yapp.giljob.domain.roadmap.api

import com.yapp.giljob.domain.roadmap.application.RoadmapService
import com.yapp.giljob.domain.roadmap.dao.RoadmapRepository
import com.yapp.giljob.domain.roadmap.dao.RoadmapScrapRepository
import com.yapp.giljob.domain.user.dao.UserRepository
import com.yapp.giljob.global.AbstractRestDocs
import com.yapp.giljob.global.common.domain.EntityFactory
import com.yapp.giljob.global.common.dto.DtoFactory
import com.yapp.giljob.global.config.security.GiljobTestUser
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(RoadmapController::class)
class RoadmapControllerTest : AbstractRestDocs() {
    @MockBean
    private lateinit var roadmapService: RoadmapService

    @MockBean
    private lateinit var roadmapRepository: RoadmapRepository

    @MockBean
    private lateinit var roadmapScrapRepository: RoadmapScrapRepository

    @MockBean
    private lateinit var userRepository: UserRepository

    @GiljobTestUser
    @Test
    fun getRoadmap() {
        BDDMockito.given(roadmapService.getRoadmapDetail(1L, EntityFactory.testUser())).willReturn(
            DtoFactory.testDetailRoadmapResponse()
        )

        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/roadmaps/{roadmapId}", 1)
        ).andDo(MockMvcResultHandlers.print())

        result
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "roadmaps/{roadmapId}/get",
                    RequestDocumentation.pathParameters(
                        RequestDocumentation.parameterWithName("roadmapId").description("로드맵 id")
                    ),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("성공 메세지"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("응답 데이터"),
                        PayloadDocumentation.fieldWithPath("data.name")
                            .description("로드맵 이름"),
                        PayloadDocumentation.fieldWithPath("data.writer")
                            .description("로드맵 작성자 정보"),
                        PayloadDocumentation.fieldWithPath("data.writer.id")
                            .description("로드맵 작성자 id"),
                        PayloadDocumentation.fieldWithPath("data.writer.nickname")
                            .description("로드맵 작성자 닉네임"),
                        PayloadDocumentation.fieldWithPath("data.writer.position")
                            .description("로드맵 작성자 직군(포지션)"),
                        PayloadDocumentation.fieldWithPath("data.writer.point")
                            .description("로드맵 작성자 직군 능력치 포인트"),
                        PayloadDocumentation.fieldWithPath("data.writer.intro")
                            .description("로드맵 작성자 자기소개"),
                        PayloadDocumentation.fieldWithPath("data.position")
                            .description("로드맵 카테고리(position)"),
                        PayloadDocumentation.fieldWithPath("data.isScraped")
                            .description("스크랩 여부"),
                        PayloadDocumentation.fieldWithPath("data.questList")
                            .description("로드맵에 등록된 퀘스트 리스트"),
                        PayloadDocumentation.fieldWithPath("data.questList[*].id")
                            .description("퀘스트 id"),
                        PayloadDocumentation.fieldWithPath("data.questList[*].name")
                            .description("퀘스트 이름"),
                        PayloadDocumentation.fieldWithPath("data.questList[*].isRealQuest")
                            .description("실제 등록된 퀘스트인지 여부")
                    )
                )
            )
    }

    @GiljobTestUser
    @Test
    fun deleteRoadmap() {
        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.delete("/api/roadmaps/{roadmapId}", 1)
        ).andDo(MockMvcResultHandlers.print())

        result
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "roadmaps/{roadmapId}/delete",
                    RequestDocumentation.pathParameters(
                        RequestDocumentation.parameterWithName("roadmapId").description("삭제할 로드맵 id")
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

}