package com.yapp.giljob.domain.sign.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.domain.sign.dto.request.SignInRequest
import com.yapp.giljob.domain.sign.dto.request.SignUpRequest
import com.yapp.giljob.domain.sign.repository.SignRepository
import com.yapp.giljob.domain.user.domain.User
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class, MockitoExtension::class)
@Transactional
class SignControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val signRepository: SignRepository
) {

    @Test
    fun `회원가입 성공`() {
        val signUpRequest = SignUpRequest(
            kakaoAccessToken = "test",
            position = Position.BACKEND.name,
            nickname = "nickname")

        val content = jacksonObjectMapper().writeValueAsString(signUpRequest)

        mockMvc
            .perform(MockMvcRequestBuilders.post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
            .andExpect(status().isOk)
            .andExpect(header().exists(HttpHeaders.AUTHORIZATION))
            .andDo(print())
    }

    @Test
    fun `로그인 성공`() {
        val user = User(
            socialId = "socialId",
            nickname = "닉네임",
            position = Position.BACKEND
        )
        signRepository.save(user)

        val signInRequest = SignInRequest(kakaoAccessToken = "test")

        val content = jacksonObjectMapper().writeValueAsString(signInRequest)

        mockMvc
            .perform(MockMvcRequestBuilders.post("/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
            .andExpect(status().isOk)
            .andExpect(header().exists(HttpHeaders.AUTHORIZATION))
            .andDo(print())

        signRepository.delete(user)
    }

    @Test
    fun `잘못된 카카오 access token으로 회원가입 시 에러`() {
        val signUpRequest = SignUpRequest(
            kakaoAccessToken = "wrong access token",
            position = Position.BACKEND.name,
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