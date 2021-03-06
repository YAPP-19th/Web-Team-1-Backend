package com.yapp.giljob.domain.roadmap.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.yapp.giljob.domain.quest.application.QuestService
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
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation
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
    private lateinit var questService: QuestService

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
                        RequestDocumentation.parameterWithName("roadmapId").description("????????? id")
                    ),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("?????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("?????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data.name")
                            .description("????????? ??????"),
                        PayloadDocumentation.fieldWithPath("data.writer")
                            .description("????????? ????????? ??????"),
                        PayloadDocumentation.fieldWithPath("data.writer.id")
                            .description("????????? ????????? id"),
                        PayloadDocumentation.fieldWithPath("data.writer.nickname")
                            .description("????????? ????????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data.writer.position")
                            .description("????????? ????????? ??????(?????????)"),
                        PayloadDocumentation.fieldWithPath("data.writer.point")
                            .description("????????? ????????? ?????? ????????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data.writer.intro")
                            .description("????????? ????????? ????????????"),
                        PayloadDocumentation.fieldWithPath("data.position")
                            .description("????????? ????????????(position)"),
                        PayloadDocumentation.fieldWithPath("data.isScraped")
                            .description("????????? ??????"),
                        PayloadDocumentation.fieldWithPath("data.questList")
                            .description("???????????? ????????? ????????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data.questList[*].id")
                            .description("????????? id"),
                        PayloadDocumentation.fieldWithPath("data.questList[*].name")
                            .description("????????? ??????"),
                        PayloadDocumentation.fieldWithPath("data.questList[*].isRealQuest")
                            .description("?????? ????????? ??????????????? ??????")
                    )
                )
            )
    }

    @GiljobTestUser
    @Test
    fun deleteRoadmap() {
        val result = mockMvc.perform(
            RestDocumentationRequestBuilders
                .delete("/api/roadmaps/{roadmapId}", 1)
                .header("Authorization", "Access Token")
        ).andDo(MockMvcResultHandlers.print())

        result
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "roadmaps/{roadmapId}/delete",
                    RequestDocumentation.pathParameters(
                        RequestDocumentation.parameterWithName("roadmapId").description("????????? ????????? id")
                    ),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("?????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("null")
                    )
                )
            )
    }

    @GiljobTestUser
    @Test
    fun saveRoadmap() {

        val questRequest = DtoFactory.testRoadmapSaveRequest()
        val jsonString = ObjectMapper().writeValueAsString(questRequest)
        val result = mockMvc.perform(
            RestDocumentationRequestBuilders
                .post("/api/roadmaps")
                .header("Authorization", "Access Token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
        ).andDo(MockMvcResultHandlers.print())

        result
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "roadmaps/post",
                    PayloadDocumentation.requestFields(
                        PayloadDocumentation.fieldWithPath("name")
                            .description("????????? ??????"),
                        PayloadDocumentation.fieldWithPath("position")
                            .description("????????? ??????"),
                        PayloadDocumentation.fieldWithPath("questList")
                            .description("????????? ????????? ?????????"),
                        PayloadDocumentation.fieldWithPath("questList[*].questId")
                            .description("?????? ???????????? ?????? ????????? ?????????, ?????? ?????? null"),
                        PayloadDocumentation.fieldWithPath("questList[*].name")
                            .description("?????? ???????????? ?????? ?????? ????????? ??????, ?????? ???????????? ?????? null"),
                    ),
                    HeaderDocumentation.responseHeaders(),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("?????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("null")
                    )
                )
            )
    }

    @Test
    fun getRoadmapList() {
        BDDMockito.given(roadmapService.getRoadmapList(Pageable.ofSize(4))).willReturn(
            listOf(
                DtoFactory.testRoadmapResponse(),
                DtoFactory.testRoadmapResponse(),
                DtoFactory.testRoadmapResponse(),
                DtoFactory.testRoadmapResponse()
            )
        )

        val result = mockMvc.perform(
            RestDocumentationRequestBuilders
                .get("/api/roadmaps")
                .param("page", "0")
                .param("size", "4")
        ).andDo(MockMvcResultHandlers.print())

        result
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "roadmaps/get",
                    RequestDocumentation.requestParameters(
                        RequestDocumentation.parameterWithName("page").description("????????? ????????? ?????????"),
                        RequestDocumentation.parameterWithName("size").description("????????? ????????? ??????")
                    ),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("?????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("?????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data[*].id")
                            .description("????????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data[*].name")
                            .description("????????? ??????"),
                        PayloadDocumentation.fieldWithPath("data[*].position")
                            .description("????????? ??????"),
                        PayloadDocumentation.fieldWithPath("data[*].writer")
                            .description("????????? ????????? ??????"),
                        PayloadDocumentation.fieldWithPath("data[*].writer.id")
                            .description("????????? ????????? id"),
                        PayloadDocumentation.fieldWithPath("data[*].writer.nickname")
                            .description("????????? ????????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data[*].writer.position")
                            .description("????????? ????????? ??????"),
                        PayloadDocumentation.fieldWithPath("data[*].writer.point")
                            .description("????????? ????????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data[*].writer.intro")
                            .description("????????? ????????? ??????"),
                    )
                )
            )
    }
}