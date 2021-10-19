package com.yapp.giljob.sign.service

import com.yapp.giljob.security.jwt.JwtProvider
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SignServiceTest {

    private val signService: SignService = SignService(jwtProvider = JwtProvider())

    @Test
    fun `getIdFromKakaoResponse 성공`() {
        val content = "{\n" +
                "    \"id\": 1,\n" +
                "    \"connected_at\": \"2021-10-18T05:26:52Z\"\n" +
                "}"

        val id = signService.getIdFromKakaoResponse(content)

        assertEquals(id, 1)
    }
}