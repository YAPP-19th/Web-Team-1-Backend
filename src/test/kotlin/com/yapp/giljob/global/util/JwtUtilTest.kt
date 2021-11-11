package com.yapp.giljob.global.util

import com.yapp.giljob.global.config.security.jwt.JwtUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder

@SpringBootTest
class JwtUtilTest {

    @Test
    fun `getUserIdFromSecurityContextHolder 성공`() {

        val userId = 1L

        val authentication: Authentication = UsernamePasswordAuthenticationToken(userId, null, null)
        SecurityContextHolder.getContext().authentication = authentication

        assertEquals(userId, JwtUtil.getUserIdFromSecurityContextHolder())
    }
}