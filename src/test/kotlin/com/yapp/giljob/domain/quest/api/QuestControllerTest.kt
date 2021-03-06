package com.yapp.giljob.domain.quest.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.yapp.giljob.domain.quest.application.QuestService
import com.yapp.giljob.domain.subquest.application.SubQuestParticipationService
import com.yapp.giljob.domain.user.dao.UserRepository
import com.yapp.giljob.global.AbstractRestDocs
import com.yapp.giljob.global.common.dto.DtoFactory
import com.yapp.giljob.global.config.security.GiljobTestUser
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.anyLong
import org.mockito.BDDMockito.given
import org.mockito.Mockito
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
class QuestControllerTest : AbstractRestDocs() {

    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    private fun <T> uninitialized(): T = null as T

    @MockBean
    private lateinit var questService: QuestService

    @MockBean
    private lateinit var userRepository: UserRepository

    @MockBean
    private lateinit var subQuestParticipationService: SubQuestParticipationService

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
    fun getContentListTest() {
        given(questService.getQuestList(any(), any())).willReturn(DtoFactory.testQuestResponse())

        val result = mockMvc.perform(
            get("/api/quests")
                .param("page", "0")
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
                        parameterWithName("page").description("페이지 번호"),
                        parameterWithName("size").description("조회할 퀘스트 개수")
                    ),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("성공 메세지"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("응답 데이터(퀘스트 리스트)"),
                        PayloadDocumentation.fieldWithPath("data.totalCount")
                            .description("퀘스트 전체 개수"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].id")
                            .description("퀘스트 id"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].name")
                            .description("퀘스트 이름"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].position")
                            .description("퀘스트 카테고리(position)"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].participantCount")
                            .description("퀘스트 참여자 수"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].writer.id")
                            .description("퀘스트 작성자 id"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].writer.nickname")
                            .description("퀘스트 작성자 nickname"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].writer.position")
                            .description("퀘스트 작성자 직군"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].writer.point")
                            .description("퀘스트 작성자 능력치"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].writer.intro")
                            .description("퀘스트 작성자 자기소개"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].difficulty")
                            .description("퀘스트 난이도"),
                        PayloadDocumentation.fieldWithPath("data.contentList[*].thumbnail")
                            .description("퀘스트 썸네일 url"),
                    )
                )
            )
    }

    @Test
    fun `getQuestDetailInfo 성공`() {
        given(questService.getQuestDetailInfo(anyLong())).willReturn(DtoFactory.testQuestDetailInfoResponse())

        val result = mockMvc.perform(
            get("/api/quests/{questId}/info", 1L)
        ).andDo(print())

        result
            .andExpect(status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
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
                        PayloadDocumentation.fieldWithPath("data.participantCount")
                            .description("퀘스트 참여자 수"),
                        PayloadDocumentation.fieldWithPath("data.writer.id")
                            .description("퀘스트 작성자 id"),
                        PayloadDocumentation.fieldWithPath("data.writer.nickname")
                            .description("퀘스트 작성자 nickname"),
                        PayloadDocumentation.fieldWithPath("data.writer.position")
                            .description("퀘스트 작성자 직군"),
                        PayloadDocumentation.fieldWithPath("data.writer.point")
                            .description("퀘스트 작성자 능력치"),
                        PayloadDocumentation.fieldWithPath("data.writer.intro")
                            .description("퀘스트 작성자 자기소개"),
                        PayloadDocumentation.fieldWithPath("data.tagList")
                            .description("퀘스트 tag list"),
                        PayloadDocumentation.fieldWithPath(("data.tagList[*].name"))
                            .description("퀘스트 tag 이름")
                    )
                )
            )
    }

    @Test
    @GiljobTestUser
    fun `getQuestDetailSubQuest 성공`() {
        given(subQuestParticipationService.getQuestDetailSubQuestProgress(anyLong(), any()))
            .willReturn(DtoFactory.testQuestDetailSubQuestResponseDto())

        val result = mockMvc.perform(
            get("/api/quests/{questId}/subquests", 1)
                .header("Authorization", "Access Token")
        ).andDo(print())

        result
            .andExpect(status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "quests/{questId}/subquests/get",
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
                        PayloadDocumentation.fieldWithPath("data.progress")
                            .description("서브 퀘스트 진행률"),
                        PayloadDocumentation.fieldWithPath("data.subQuestProgressList")
                            .description("서브 퀘스트 진행 리스트"),
                        PayloadDocumentation.fieldWithPath("data.subQuestProgressList[*].subQuestId")
                            .description("서브 퀘스트 아아디"),
                        PayloadDocumentation.fieldWithPath("data.subQuestProgressList[*].subQuestName")
                            .description("서브 퀘스트 이름"),
                        PayloadDocumentation.fieldWithPath("data.subQuestProgressList[*].isCompleted")
                            .description("서브 퀘스트 진행 여부"),
                    )
                )
            )
    }
}