package com.yapp.giljob.global.config.security.jwt

import com.yapp.giljob.global.config.security.user.UserDetailsServiceImpl
import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtResolver(
    private val userDetailsService: UserDetailsServiceImpl
) {
    private val secretKey: String = Base64.getEncoder().encodeToString("secretKey".encodeToByteArray())

    private fun getAuthenticationUserId(token: String?): String {
        return Jwts.parser().setSigningKey(secretKey)
            .parseClaimsJws(token).body.subject
    }

    fun getAuthentication(token: String?): Authentication {
        val user: UserDetails? = userDetailsService.loadUserByUsername(getAuthenticationUserId(token))
        return UsernamePasswordAuthenticationToken(user, "", null)
    }

}