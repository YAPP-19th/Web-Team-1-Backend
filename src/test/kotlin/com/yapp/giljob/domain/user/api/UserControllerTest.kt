package com.yapp.giljob.domain.user.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.yapp.giljob.domain.user.application.UserService
import com.yapp.giljob.domain.user.dao.AbilityRepository
import com.yapp.giljob.domain.user.dao.UserRepository
import com.yapp.giljob.global.AbstractRestDocs
import com.yapp.giljob.global.common.domain.EntityFactory
import com.yapp.giljob.global.common.dto.DtoFactory
import com.yapp.giljob.global.config.security.GiljobTestUser
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(UserController::class)
class UserControllerTest : AbstractRestDocs() {

    @MockBean
    private lateinit var userService: UserService

    @MockBean
    private lateinit var abilityRepository: AbilityRepository

    @MockBean
    private lateinit var userRepository: UserRepository

    @GiljobTestUser
    @Test
    fun getAuthenticatedUserInfoTest() {
        BDDMockito.given(userService.getUserInfo(EntityFactory.testUser())).willReturn(
            DtoFactory.testUserInfoResponse()
        )

        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/users/me")
                .header("Authorization", "Access Token")
        ).andDo(MockMvcResultHandlers.print())

        result
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "users/me/get",
                    HeaderDocumentation.responseHeaders(),
                    HeaderDocumentation.responseHeaders(),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("?????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("?????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data.id")
                            .description("?????? ?????? id"),
                        PayloadDocumentation.fieldWithPath("data.nickname")
                            .description("?????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data.position")
                            .description("????????? ????????? ??????(?????????)"),
                        PayloadDocumentation.fieldWithPath("data.point")
                            .description("????????? ????????? ????????? ?????? ????????? ?????????(?????? ??????)"),
                        PayloadDocumentation.fieldWithPath("data.intro")
                            .description("?????? ????????????")
                    )
                )
            )
    }

    @GiljobTestUser
    @Test
    fun getUserProfileTest() {
        BDDMockito.given(userService.getUserProfile(1L)).willReturn(
            DtoFactory.testUserProfileResponse()
        )

        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/users/{userId}/profile", 1L)
        ).andDo(MockMvcResultHandlers.print())

        result
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "users/{userId}/profile/get",
                    HeaderDocumentation.responseHeaders(),
                    HeaderDocumentation.responseHeaders(),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("?????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("?????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data.userInfo")
                            .description("?????? ?????? ??????"),
                        PayloadDocumentation.fieldWithPath("data.userInfo.id")
                            .description("?????? id"),
                        PayloadDocumentation.fieldWithPath("data.userInfo.nickname")
                            .description("?????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data.userInfo.position")
                            .description("????????? ????????? ??????(?????????)"),
                        PayloadDocumentation.fieldWithPath("data.userInfo.point")
                            .description("????????? ????????? ????????? ?????? ????????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data.userInfo.intro")
                            .description("?????? ????????????"),
                        PayloadDocumentation.fieldWithPath("data.achieve")
                            .description("?????? ?????? ??????"),
                        PayloadDocumentation.fieldWithPath("data.achieve.pointAchieve")
                            .description("????????? ??????"),
                        PayloadDocumentation.fieldWithPath("data.achieve.questAchieve")
                            .description("????????? ??????"),
                        PayloadDocumentation.fieldWithPath("data.abilityList")
                            .description("?????? ????????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data.abilityList[*].position")
                            .description("?????? ?????????"),
                        PayloadDocumentation.fieldWithPath("data.abilityList[*].point")
                            .description("?????? ???????????? ?????? ?????????"),
                    )
                )
            )
    }

    @GiljobTestUser
    @Test
    fun updateUserInfoTest() {
        val request = DtoFactory.testUserInfoRequest()
        val jsonString = ObjectMapper().writeValueAsString(request)
        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.patch("/api/users/me")
                .header("Authorization", "Access Token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
        ).andDo(MockMvcResultHandlers.print())

        result
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "users/me/patch",
                    HeaderDocumentation.responseHeaders(),
                    HeaderDocumentation.responseHeaders(),
                    PayloadDocumentation.requestFields(
                        PayloadDocumentation.fieldWithPath("nickname")
                            .description("????????? ?????????"),
                        PayloadDocumentation.fieldWithPath("position")
                            .description("????????? ??????(BACKEND/FRONTEND)"),
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
    fun updateUserIntroTest() {
        val request = DtoFactory.testUserIntroRequest()
        val jsonString = ObjectMapper().writeValueAsString(request)
        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.patch("/api/users/me/intro")
                .header("Authorization", "Access Token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
        ).andDo(MockMvcResultHandlers.print())

        result
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "users/me/intro/patch",
                    HeaderDocumentation.responseHeaders(),
                    HeaderDocumentation.responseHeaders(),
                    PayloadDocumentation.requestFields(
                        PayloadDocumentation.fieldWithPath("intro")
                            .description("????????? ????????????"),
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
}