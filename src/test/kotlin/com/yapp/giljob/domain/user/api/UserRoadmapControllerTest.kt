package com.yapp.giljob.domain.user.api

import com.yapp.giljob.domain.user.application.UserRoadmapService
import com.yapp.giljob.domain.user.dao.UserRepository
import com.yapp.giljob.global.AbstractRestDocs
import com.yapp.giljob.global.common.dto.DtoFactory
import com.yapp.giljob.global.common.dto.ListResponseDto
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.Pageable
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(UserRoadmapController::class)
class UserRoadmapControllerTest : AbstractRestDocs() {

    @MockBean
    private lateinit var userRoadmapService: UserRoadmapService

    @MockBean
    private lateinit var userRepository: UserRepository

    private val userId = 1L

    @Test
    fun getScrapRoadmapListByUserId() {
        BDDMockito.given(userRoadmapService.getScrapRoadmapListByUser(userId, Pageable.ofSize(6))).willReturn(
            ListResponseDto(
                totalCount = 6,
                contentList = listOf(
                    DtoFactory.testRoadmapResponse().apply { this.id = 6L; this.name = "로드맵 테스트 6" },
                    DtoFactory.testRoadmapResponse().apply { this.id = 5L; this.name = "로드맵 테스트 5" },
                    DtoFactory.testRoadmapResponse().apply { this.id = 4L; this.name = "로드맵 테스트 4" },
                    DtoFactory.testRoadmapResponse().apply { this.id = 3L; this.name = "로드맵 테스트 3" },
                    DtoFactory.testRoadmapResponse().apply { this.id = 2L; this.name = "로드맵 테스트 2" },
                    DtoFactory.testRoadmapResponse().apply { this.id = 1L; this.name = "로드맵 테스트 1" },
                )
            )
        )

        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/users/{userId}/roadmaps/scrap", userId)
                .param("page", "0")
                .param("size", "6")
        ).andDo(MockMvcResultHandlers.print())

        result
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "users/{userId}/roadmaps/scrap/get",
                    HeaderDocumentation.responseHeaders(),
                    HeaderDocumentation.responseHeaders(),
                    RequestDocumentation.pathParameters(
                        RequestDocumentation.parameterWithName("userId").description("조회할 유저 id"),
                    ),
                    RequestDocumentation.requestParameters(
                        RequestDocumentation.parameterWithName("page")
                            .description("조회할 페이지"),
                        RequestDocumentation.parameterWithName("size").description("조회할 로드맵 개수"),
                    ),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("성공 메세지"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("로드맵 response"),
                        PayloadDocumentation.fieldWithPath("data.totalCount")
                            .description("로드맵 개수"),
                        PayloadDocumentation.fieldWithPath("data.contentList")
                            .description("로드맵 리스트"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].id")
                            .description("로드맵 id"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].name")
                            .description("로드맵 이름"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].position")
                            .description("로드맵 카테고리(position)"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].writer.id")
                            .description("로드맵 작성자 id"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].writer.nickname")
                            .description("로드맵 작성자 nickname"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].writer.position")
                            .description("로드맵 작성자 직군"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].writer.point")
                            .description("로드맵 작성자 능력치"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].writer.intro")
                            .description("로드맵 작성자 자기소개")
                    )
                )
            )
    }

    @Test
    fun getMyRegisteredRoadmapList() {
        BDDMockito.given(userRoadmapService.getRoadmapListByUser(userId, Pageable.ofSize(6))).willReturn(
            ListResponseDto(
                totalCount = 6,
                contentList = listOf(
                    DtoFactory.testRoadmapResponse(),
                    DtoFactory.testRoadmapResponse(),
                    DtoFactory.testRoadmapResponse(),
                    DtoFactory.testRoadmapResponse(),
                    DtoFactory.testRoadmapResponse(),
                    DtoFactory.testRoadmapResponse()
                )
            )
        )

        val result = mockMvc.perform(
            RestDocumentationRequestBuilders
                .get("/api/users/{userId}/roadmaps", userId)
                .param("page", "0")
                .param("size", "6")
        ).andDo(MockMvcResultHandlers.print())

        result
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "users/{userId}/roadmaps/get",
                    RequestDocumentation.requestParameters(
                        RequestDocumentation.parameterWithName("page")
                            .description("조회할 페이지"),
                        RequestDocumentation.parameterWithName("size").description("조회할 로드맵 개수")
                    ),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("성공 메세지"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("로드맵 response"),
                        PayloadDocumentation.fieldWithPath("data.totalCount")
                            .description("로드맵 개수"),
                        PayloadDocumentation.fieldWithPath("data.contentList")
                            .description("로드맵 리스트"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].id")
                            .description("로드맵 id"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].name")
                            .description("로드맵 이름"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].position")
                            .description("로드맵 카테고리(position)"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].writer.id")
                            .description("로드맵 작성자 id"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].writer.nickname")
                            .description("로드맵 작성자 nickname"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].writer.position")
                            .description("로드맵 작성자 직군"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].writer.point")
                            .description("로드맵 작성자 능력치"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].writer.intro")
                            .description("로드맵 작성자 자기소개")
                    )
                )
            )
    }
}