package com.yapp.giljob.domain.sign.converter

import com.yapp.giljob.domain.sign.dto.request.SignUpRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SignConverterTest {

    @Test
    fun `signUpConverterToModel 성공`() {
        val converter = Mappers.getMapper(SignConverter::class.java)

        val signUpRequest = SignUpRequest(
            kakaoAccessToken = "kakao access token",
            positionId = 1L,
            nickname = "nickname")
        val user = converter.signUpConvertToModel(signUpRequest)

        assertEquals(user.nickname, signUpRequest.nickname)
    }

    @Test
    fun `SignUpRequest에 잘못된 필드 값 있을 경우 에러`() {
        TODO("Not yet implemented")
    }
}