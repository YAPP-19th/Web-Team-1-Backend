package com.yapp.giljob.global.config.security.jwt

import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.global.util.JwtUtil
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class, MockitoExtension::class)
class WebSecurityConfigTest{

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `필터 통과 안하기 성공`() {
        mockMvc
            .perform(post("/don't-pass-filter"))
            .andExpect(status().isNotFound)
            .andDo(print())
    }

    @Test
    fun `access token 으로 필터 통과하기 성공`() {
        val accessToken = JwtUtil.createAccessToken(1L)

        mockMvc
            .perform(post("/pass-filter-with-token").header("Authorization", accessToken))
            .andExpect(status().isNotFound)
            .andDo(print())
    }

    @Test
    fun `토큰 없음 에러`() {
        mockMvc
            .perform(post("/no-token-error"))
            .andExpect(status().isForbidden)
            .andExpect(jsonPath("$.code").value(ErrorCode.NO_TOKEN_ERROR.code))
            .andExpect(jsonPath("$.message").value(ErrorCode.NO_TOKEN_ERROR.message))
            .andDo(print())
    }

    @Test
    fun `유효하지 않은 access token 에러`() {
        val accessToken: String = "invalid access token"

        mockMvc
            .perform(post("/invalid-access-token").header("Authorization", accessToken))
            .andExpect(status().isForbidden)
            .andExpect(jsonPath("$.code").value(ErrorCode.INVALID_TOKEN_ERROR.code))
            .andExpect(jsonPath("$.message").value(ErrorCode.INVALID_TOKEN_ERROR.message))
            .andDo(print())
    }

    @Test
    fun `expired access token 에러`() {
        val accessToken: String = JwtUtil.createToken("expired access token", 1)

        mockMvc
            .perform(post("/expired-access-token").header("Authorization", accessToken))
            .andExpect(status().isForbidden)
            .andExpect(jsonPath("$.code").value(ErrorCode.EXPIRED_TOKEN_ERROR.code))
            .andExpect(jsonPath("$.message").value(ErrorCode.EXPIRED_TOKEN_ERROR.message))
            .andDo(print())
    }
}