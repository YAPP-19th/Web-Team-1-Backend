package com.yapp.giljob.domain.sign.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.yapp.giljob.domain.sign.repository.SignRepository
import com.yapp.giljob.global.AbstractRestDocs
import com.yapp.giljob.global.common.domain.EntityFactory
import com.yapp.giljob.global.common.dto.DtoFactory
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
internal class SignControllerTest : AbstractRestDocs() {

    @MockBean
    private lateinit var signRepository: SignRepository

    private val user = EntityFactory.testUser()
    private val signUpRequest = DtoFactory.testSignUpRequest()
    private val signInRequest = DtoFactory.testSignInRequest()

    @Test
    fun signUp() {
        val jsonString = ObjectMapper().writeValueAsString(signUpRequest)

        val result = mockMvc
            .perform(
                MockMvcRequestBuilders.post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.header().exists(HttpHeaders.AUTHORIZATION))
            .andDo(MockMvcResultHandlers.print())

        result
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "sign-up/post",
                    HeaderDocumentation.responseHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION)
                            .description("어플리케이션 access token")
                    ),
                    PayloadDocumentation.requestFields(
                        PayloadDocumentation.fieldWithPath("kakaoAccessToken")
                            .description("카카오 access token"),
                        PayloadDocumentation.fieldWithPath("position")
                            .description("카테고리(직군)"),
                        PayloadDocumentation.fieldWithPath("nickname")
                            .description("닉네임")
                    )
                )
            )
    }

    @Test
    fun signIn() {
        given(signRepository.findBySocialId(anyString())).willReturn(user)

        val jsonString = ObjectMapper().writeValueAsString(signInRequest)

        val result = mockMvc
            .perform(
                MockMvcRequestBuilders.post("/sign-in")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonString))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.header().exists(HttpHeaders.AUTHORIZATION))
            .andDo(MockMvcResultHandlers.print())

        result
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "sign-in/post",
                    HeaderDocumentation.responseHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION)
                            .description("어플리케이션 access token")
                    ),
                    PayloadDocumentation.requestFields(
                        PayloadDocumentation.fieldWithPath("kakaoAccessToken")
                            .description("카카오 access token")
                    )
                )
            )

    }
}