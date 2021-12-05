package com.yapp.giljob.domain.user.api

import com.yapp.giljob.domain.user.application.UserService
import com.yapp.giljob.domain.user.dao.AbilityRepository
import com.yapp.giljob.domain.user.dao.UserRepository
import com.yapp.giljob.domain.user.dto.response.UserInfoResponseDto
import com.yapp.giljob.global.AbstractRestDocs
import com.yapp.giljob.global.common.domain.EntityFactory
import com.yapp.giljob.global.config.security.GiljobTestUser
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
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
        BDDMockito.given(userService.getAuthenticatedUserInfo(EntityFactory.testUser())).willReturn(
            UserInfoResponseDto(1L, 1000)
        )

        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/users/me")
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
                            .description("성공 메세지"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("응답 데이터"),
                        PayloadDocumentation.fieldWithPath("data.userId")
                            .description("현재 유저 id"),
                        PayloadDocumentation.fieldWithPath("data.point")
                            .description("유저 능력치 포인트(레벨 결정)")
                    )
                )
            )
    }
}