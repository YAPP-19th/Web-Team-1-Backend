package com.yapp.giljob.global.config.security.jwt

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*
import javax.servlet.http.HttpServletRequest

class JwtUtil {
    companion object {
        private val secretKey: String = Base64.getEncoder().encodeToString("secretKey".encodeToByteArray())

        @Value("\${spring.social.tokenTime}")
        private val accessTokenValidTime: String = "100000000"

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

        fun getTokenFromHeader(httpServletRequest: HttpServletRequest): String {
            val token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION)
            return token.toString().replace("Bearer", "").trim()
        }

        fun getUserIdFromSecurityContextHolder(): Long {
            return SecurityContextHolder.getContext().authentication.principal.toString().toLong()
        }

        fun validateToken(token: String?): Boolean {
            return try {
                Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                true
            } catch (e: ExpiredJwtException) {
                false
            } catch (e: JwtException) {
                false
            } catch (e: IllegalArgumentException) {
                false
            }
        }
    }
}