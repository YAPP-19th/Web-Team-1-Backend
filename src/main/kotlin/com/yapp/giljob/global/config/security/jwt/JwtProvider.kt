package com.yapp.giljob.global.config.security.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtProvider {
    private val secretKey: String = Base64.getEncoder().encodeToString("secretKey".encodeToByteArray())

    @Value("\${spring.social.tokenTime}")
    private lateinit var accessTokenValidTime: String

    fun createAccessToken(id: Long?): String {
        val sub = id.toString()
        return createToken(sub, accessTokenValidTime.toLong())
    }

    fun createToken(sub: String, validTime: Long): String {
        val claims = Jwts.claims().setSubject(sub)
        val date = Date()
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(date)
            .setExpiration(Date(date.time + validTime))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }

    fun decodeToken(token: String) {
        Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .body
    }

    fun getTokenFromHeader(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse): String {
        val token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION)
        return token.toString().replace("Bearer", "").trim()
    }
}