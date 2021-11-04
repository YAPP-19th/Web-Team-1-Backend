package com.yapp.giljob.domain.sign.service

import com.yapp.giljob.global.util.KakaoUtil.Companion.getIdFromKakaoResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SignServiceTest (
    @Autowired
    private val signService: SignService
    ) {

    @Test
    fun `returnWithAccessToken 성공`() {
        TODO("Not yet implemented")
    }

    @Test
    fun `기가입자가 회원가입시 에러`() {

    }

    @Test
    fun `미가입자가 로그인시 에러`() {
        TODO("Not yet implemented")
    }
}