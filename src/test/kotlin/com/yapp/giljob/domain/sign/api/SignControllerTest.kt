package com.yapp.giljob.domain.sign.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.yapp.giljob.domain.sign.repository.SignRepository
import com.yapp.giljob.global.AbstractRestDocs
import com.yapp.giljob.global.common.domain.EntityFactory
import com.yapp.giljob.global.common.dto.DtoFactory
import com.yapp.giljob.infra.kakao.application.KakaoService
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.anyString
import org.mockito.BDDMockito.given
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
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

    @MockBean
    private lateinit var kakaoService: KakaoService

    private val user = EntityFactory.testUser()
    private val signUpRequest = DtoFactory.testSignUpRequest()
    private val signInRequest = DtoFactory.testSignInRequest()

    @Test
    fun signUp() {
        given(kakaoService.getKakaoIdFromToken(anyString())).willReturn(user.socialId)

        val jsonString = ObjectMapper().writeValueAsString(signUpRequest)

        val result = mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())

        result
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "sign-up/post",
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("성공 메세지"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("응답 데이터(퀘스트 리스트)"),
                        PayloadDocumentation.fieldWithPath("data.accessToken")
                            .description("애플리케이션 access token")
                    ),
                    PayloadDocumentation.requestFields(
                        PayloadDocumentation.fieldWithPath("kakaoAccessToken")
                            .description("카카오 access token"),
                        PayloadDocumentation.fieldWithPath("position")
                            .description("카테고리(직군)"),
                        PayloadDocumentation.fieldWithPath("intro")
                            .description("자기 소개"),
                        PayloadDocumentation.fieldWithPath("nickname")
                            .description("닉네임")
                    )
                )
            )
    }

    @Test
    fun signIn() {
        given(signRepository.findBySocialId(anyString())).willReturn(user)
        given(kakaoService.getKakaoIdFromToken(anyString())).willReturn(user.socialId)

        val jsonString = ObjectMapper().writeValueAsString(signInRequest)

        val result = mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/sign-in")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonString))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())

        result
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "sign-in/post",
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("성공 메세지"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("응답 데이터(퀘스트 리스트)"),
                        PayloadDocumentation.fieldWithPath("data.isSignedUp")
                            .description("회원 가입한 회원인지 여부, false이면 회원 가입 진행"),
                        PayloadDocumentation.fieldWithPath("data.accessToken")
                            .description("애플리케이션 access token")
                    ),
                    PayloadDocumentation.requestFields(
                        PayloadDocumentation.fieldWithPath("kakaoAccessToken")
                            .description("카카오 access token")
                    )
                )
            )

    }
}