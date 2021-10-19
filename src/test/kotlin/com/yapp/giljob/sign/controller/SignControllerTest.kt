package com.yapp.giljob.sign.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.sign.dto.request.SignInRequest
import com.yapp.giljob.sign.dto.request.SignUpRequest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class, MockitoExtension::class)
class SignControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `잘못된 카카오 access token으로 회원가입 시 에러`() {
        val signUpRequest = SignUpRequest(
            kakaoAccessToken = "wrong access token",
            positionId = 1L,
            nickname = "nickname")

        val content = jacksonObjectMapper().writeValueAsString(signUpRequest)

        mockMvc
            .perform(MockMvcRequestBuilders.post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
            .andExpect(status().isInternalServerError)
            .andExpect(MockMvcResultMatchers.jsonPath("$.code")
                .value(ErrorCode.CAN_NOT_GET_KAKAO_ID_ERROR.code))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                .value(ErrorCode.CAN_NOT_GET_KAKAO_ID_ERROR.message))
            .andDo(print())
    }

    @Test
    fun `잘못된 카카오 access token으로 로그인 시 에러`() {
        val signInRequest = SignInRequest(
            kakaoAccessToken = "wrong access token")

        val content = jacksonObjectMapper().writeValueAsString(signInRequest)

        mockMvc
            .perform(MockMvcRequestBuilders.post("/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
            .andExpect(status().isInternalServerError)
            .andExpect(MockMvcResultMatchers.jsonPath("$.code")
                .value(ErrorCode.CAN_NOT_GET_KAKAO_ID_ERROR.code))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                .value(ErrorCode.CAN_NOT_GET_KAKAO_ID_ERROR.message))
    }

}